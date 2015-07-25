package com.azmotors.store.controller.Command.handlers;

import java.text.DateFormat;
import java.util.Date;

import javafx.scene.control.TableView;

import com.azmotors.store.controller.AZController;
import com.azmotors.store.model.IndustryPartElement;

public final class AttemptSellCommandHandler implements IStoreCommandHandler
{
    private final AZController m_controller;
    private final TableView<? extends IndustryPartElement> m_tableView;

    public AttemptSellCommandHandler(final TableView<? extends IndustryPartElement> tableView, final AZController controller)
    {
        super();
        assert null != tableView : "Parameter 'tableView' of method 'SellCommandHandler' must not be null";
        assert null != controller : "Parameter 'controller' of method 'AttemptSellCommandHandler' must not be null";
        m_tableView = tableView;
        m_controller =controller;
    }

    @Override
    public void handle(final int rowSelectedIndex)
    {
        final IndustryPartElement element = m_tableView.getItems().get(rowSelectedIndex);
        element.setDateSold(DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date()));
        m_controller.sold(element);
    }
}
