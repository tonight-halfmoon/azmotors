package com.azmotors.store.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.controller.AZController;
import com.azmotors.store.controller.Command.handlers.IStoreCommandHandler;
import com.azmotors.store.model.IndustryPartElement;

public final class TableCellButton extends TableCell<IndustryPartElement, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCellButton.class);

    private final AZController m_controller;
    private final Button m_button;
    private final IStoreCommandHandler m_sellCommandHandler;

    public TableCellButton(final AZController controller, final String label, IStoreCommandHandler sellCommandHandler)
    {
        assert null != controller : "Parameter 'controller' of method 'TableCellButton' must not be null";
        assert null != label && !label.isEmpty() : "Parameter 'label' of method 'TableCellButton' must not be empty";
        assert null != sellCommandHandler : "Parameter 'sellCommandHandler' of method 'TableCellButton' must not be null";
        m_controller = controller;
        m_button = new Button(label);
        m_sellCommandHandler = sellCommandHandler;
    }

    private void configure()
    {
        m_button.setPrefWidth(76d);
        m_button.setMinWidth(76d);

        setGraphic(m_button);
        m_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                final int selectedRowIndex = getTableRow().getIndex();
                getTableView().getSelectionModel().select(selectedRowIndex);
                m_sellCommandHandler.handle(selectedRowIndex);
                m_controller.valueModified(selectedRowIndex);
            }
        });
    }

    @Override
    protected void updateItem(final String item, final boolean empty)
    {
        if (empty)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Parameter 'empty' in method 'updateItem' is {} ", String.valueOf(empty));
            }
            setText(null);
            setGraphic(null);
        }
        else
        {
            configure();
        }
    }
}
