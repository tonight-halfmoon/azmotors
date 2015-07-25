package com.azmotors.store.foundation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.controller.repo.RepoOnDiskPointer;
import com.azmotors.store.model.Constants;

public final class FileUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    private final static String USER_HOME_DIR = System.getProperty("user.home");
    private final static String FILE_SEPARATOR = System.getProperty("file.separator");
    private final static String AZMOTORS_FOLDER_NAME = ".AZMotors";
    private final static String m_absPath2AZmotorsUserFolder = USER_HOME_DIR + FILE_SEPARATOR + AZMOTORS_FOLDER_NAME;
    private final static String _BACKUP = "_backup";

    private FileUtil()
    {
        super();
    }

    public static void write(final List<String> lines, final RepoOnDiskPointer deriveSpecificRepoOnDiskPointer)
    {
        /** over - write **/
        write(lines, false, deriveSpecificRepoOnDiskPointer);
    }

    private static void write(final Collection<String> lines, final boolean append, final RepoOnDiskPointer deriveSpecificRepoOnDiskPointer)
    {
        assert null != lines && !lines.isEmpty() : "Parameter 'lines' of method 'write' must not be empty";
        assert null != deriveSpecificRepoOnDiskPointer : "Parameter 'deriveSpecificRepoOnDiskPointer' of method 'write' must not be null";

        final File azmotorsDir = new File(m_absPath2AZmotorsUserFolder);
        if (!azmotorsDir.exists())
        {
            azmotorsDir.mkdir();
            azmotorsDir.setReadOnly();
        }
        try
        {
            final File outFile = new File(m_absPath2AZmotorsUserFolder + FILE_SEPARATOR + deriveSpecificRepoOnDiskPointer.point());

            if (outFile.exists())
            {
                try
                {
                    makeBackup(outFile);
                }
                catch (IOException | URISyntaxException e)
                {
                    LOGGER.error(e.getMessage());
                }
            }
            if (!append)
            {
                if (outFile.exists())
                {
                    outFile.setWritable(true);
                }
                FileUtils.writeLines(outFile, Constants.UTF_8, lines);
                outFile.setReadOnly();
            }
            else
            {
                FileUtils.writeLines(outFile, Constants.UTF_8, lines, true);
            }
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
        }
        if (azmotorsDir.exists())
        {
            azmotorsDir.setReadOnly();
        }
    }

    private static void makeBackup(final File fileSource) throws IOException, URISyntaxException
    {
        assert null != fileSource : "Parameter 'fileSource' of method 'makeBackup' must not be null";
        /**
         * A copy using apache commons {@link FileUtils#copyFile} method in case of my method does not work.
         */
        final File backupTargetFile = new File(fileSource.getParentFile().getAbsolutePath() + FILE_SEPARATOR + fileSource.getName() + _BACKUP
                + Constants.DASH + DateUtil.today() + "_S.txt");
        if (backupTargetFile.exists())
        {
            backupTargetFile.setWritable(true); 
        }
        FileUtils.copyFile(fileSource, backupTargetFile);
        backupTargetFile.setReadOnly();

        final URL[] urls = FileUtils.toURLs(new File[] { fileSource });
        if (null == urls)
        {
            LOGGER.error("Local variable 'urls' of method 'makeBackup' is null");
        }
        final Path pathSource = Paths.get(urls[0].toURI());
        final String parentAbsPath = fileSource.getParentFile().getAbsolutePath();
        final String targetFileAbsPath = postFixFileNameBackup(fileSource.getName());
        final File targetFile = new File(parentAbsPath + FILE_SEPARATOR + targetFileAbsPath);

        targetFile.createNewFile();

        final URL[] urls2 = FileUtils.toURLs(new File[] { targetFile });
        if (null == urls2)
        {
            LOGGER.error("Local variable 'urls2' of method 'makeBackup' is null");
        }
        else
        {
            final Path pathTarget = Paths.get(urls2[0].toURI());
            final File backupTargetFile2 = pathTarget.toFile();
            if (backupTargetFile2 .exists())
            {
                backupTargetFile2 .setWritable(true);
            }
            Files.copy(pathSource, pathTarget, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            pathTarget.toFile().setReadOnly();
            LOGGER.info("source '{}' file has been backed up as '{}'", pathSource, pathTarget);
        }
    }

    private static String postFixFileNameBackup(final String fileNameWithExtension)
    {
        assert null != fileNameWithExtension && !fileNameWithExtension.isEmpty() : "Parameter 'fileNameWithExtension' of method 'postFixFileNameBackup' must not be empty";
        return fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf(Constants.DOT)) + _BACKUP + Constants.DASH + DateUtil.today()
                + fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf(Constants.DOT));
    }

    public static Collection<String> read(final String repoOnDisk)
    {
        assert null != repoOnDisk && !repoOnDisk.isEmpty() : "Parameter 'repoOnDisk' of method 'read' must not be empty";

        final File azmotorsDir = new File(m_absPath2AZmotorsUserFolder);
        if (!azmotorsDir.exists())
        {
            LOGGER.info("The directory '{}' still does not exist.", m_absPath2AZmotorsUserFolder);
            return Collections.emptyList();
        }
        try
        {
            final File file = new File(m_absPath2AZmotorsUserFolder + FILE_SEPARATOR + repoOnDisk);
            if (!file.exists())
            {
                LOGGER.error("'File '{}' does not exist. The data file will be created once you enter the first element.", file.getAbsolutePath());
                return Collections.emptyList();
            }
            return FileUtils.readLines(file, Constants.UTF_8);
        }
        catch (IOException e)
        {
            LOGGER.error("Unable to read the file '{}'", m_absPath2AZmotorsUserFolder + FILE_SEPARATOR + repoOnDisk);
            LOGGER.error(e.getMessage());
        }
        return Collections.emptyList();
    }
}
