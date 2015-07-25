package com.azmotors.store.foundation;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.controller.repo.RepoOnDiskPointer;
import com.azmotors.store.model.Constants;
import com.azmotors.store.model.IndustryElementNull;
import com.azmotors.store.model.IndustryPartElement;

public final class DataIOFromRepo
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataIOFromRepo.class);

    private static final DataIOFromRepo INSTANCE = new DataIOFromRepo();

    private final ObservableList<IndustryPartElement> m_repo;

    public static DataIOFromRepo getInstance()
    {
        return INSTANCE;
    }

    public ObservableList<IndustryPartElement> getmRepo(final RepoOnDiskPointer pointer)
    {
        assert null != pointer : "Parameter 'pointer' of method 'getmRepo' must not be null";

        final Collection<String> lines = FileUtil.read(pointer.point());
        for (final String nextLine : lines)
        {
            final String[] tokens = nextLine.split(Constants.TOKEN_EQUAL);
            final IndustryPartElement next = pointer.buildObject(tokens);
            if (null == next || next instanceof IndustryElementNull)
            {
                LOGGER.warn("One element of type '{}' was not serialised!");
            }
            else
            {
                m_repo.add(next);
            }
        }
        if (m_repo.isEmpty())
        {
            LOGGER.warn("'m_repo' is empty");
        }
        return m_repo;
    }

    private DataIOFromRepo()
    {
        super();
        m_repo = FXCollections.observableArrayList();
    }
}
