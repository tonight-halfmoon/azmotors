package com.azmotors.store.view;

import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.IllegalFormatException;
import java.util.Locale;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.controller.IHeapStatusPublisher;
import com.azmotors.store.foundation.runtime.RuntimeInfo;

public final class HeapStatusHBox extends HBox implements IHeapStatusPublisher
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HeapStatusHBox.class);
    private final Label m_labelHeapStatus;

    public HeapStatusHBox()
    {
        m_labelHeapStatus = new Label();
        final Label m_lableHeapLabel = new Label();
        m_lableHeapLabel.setText("VM Heap memory usage: ");
        final HBox heapBox = new HBox();
        heapBox.getChildren().add(m_lableHeapLabel);
        heapBox.getChildren().add(m_labelHeapStatus);
        this.getChildren().add(heapBox);
        this.setPadding(new Insets(7));
        this.setSpacing(67);
        publishVendor();
        final Thread heapStatusRefreshThread = new Thread(new HeapStatusPublisher(this));
        heapStatusRefreshThread.setDaemon(true);
        heapStatusRefreshThread.start();
    }

    @Override
    public void publish(long[] memoryStatus)
    {
        publishHeapStatus(memoryStatus);
    }

    /**
     * It is also possible to extends Task from javafx.concurrent instead of implementing Runnable. In that case, you need to override
     * {@link Task#call} method instead of overriding run method.
     */
    protected final class HeapStatusPublisher implements Runnable
    {
        private final IHeapStatusPublisher m_publisher;

        protected HeapStatusPublisher(final IHeapStatusPublisher publisher)
        {
            m_publisher = publisher;
        }

        @Override
        public void run()
        {
            while (true)
            {
                m_publisher.publish(RuntimeInfo.deriveMemoryStatus());
                try
                {
                    Thread.sleep(20 * 1000);
                }
                catch (InterruptedException e)
                {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    private void publishVendor()
    {
        final Label lblVMVersion = new Label();
        lblVMVersion.setText(RuntimeInfo.provideVendorInfo());
        this.getChildren().add(lblVMVersion);
    }

    private void amendHeapStatusField(final Label labelHeapStatus, final Formatter formatter)
    {
        if (null == formatter)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Parameter 'formatter' of method 'amendHeapStatusField' must not be null");
            }
            return;
        }
        if (null == labelHeapStatus)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Parameter 'labelHeapStatus' of method 'amendHeapStatusField' must not be null");
            }
            return;
        }
        labelHeapStatus.setText(formatter.toString());
        formatter.close();
    }

    private void publishHeapStatus(final long memoryStatus[])
    {
        if (null == memoryStatus)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Parameter 'memoryStatus' of method 'publishHeapStatus' must not be null");
            }
        }
        else
        {
            final StringBuilder sb = new StringBuilder();
            sb.append("%d M of %d M");
            try
            {
                final Formatter formatter = new Formatter(Locale.UK);
                formatter.format(sb.toString(), memoryStatus[0], memoryStatus[1]);

                Platform.runLater(() -> amendHeapStatusField(m_labelHeapStatus, formatter));
                /**
                 * If Push mode is not set Then, set it before you invoke push method If set to manual then you need to explicitly invoke method
                 * UI.getCurrent().push();
                 */
            }
            catch (IllegalFormatException | FormatterClosedException e)
            {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
