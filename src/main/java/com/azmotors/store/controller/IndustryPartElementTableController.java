package com.azmotors.store.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.controller.Command.handlers.AttemptSellCommandHandler;
import com.azmotors.store.controller.predicate.IndustryPartElementPredicate;
import com.azmotors.store.controller.repo.RepoOnDiskPointer;
import com.azmotors.store.foundation.FileUtil;
import com.azmotors.store.model.Constants;
import com.azmotors.store.model.IndustryPartElement;
import com.azmotors.store.view.TableCellButton;

abstract class IndustryPartElementTableController extends AZController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndustryPartElementTableController.class);

    protected ObservableList<IndustryPartElement> m_repo;

    private final FilteredList<IndustryPartElement> m_filteredList;
    private final IndustryPartElementPredicate m_industryPartElementPredicate;
    private Set<TextInputControl> m_clearableControlSet;

    @FXML
    private TableView<IndustryPartElement> m_industryPartsElementTableView;

    @FXML
    private Label m_totalIndustryPartsElementAmount;
    @FXML
    private Label m_soldIndustryPartsElementAmount;
    @FXML
    private Label m_inStoreIndustryPartsElementAmount;

    @FXML
    private Button m_add;
    @FXML
    protected TextField m_addName;
    @FXML
    protected TextField m_addKW;
    @FXML
    protected TextField m_addHP;
    @FXML
    protected TextField m_addRPM;
    @FXML
    protected TextField m_addVolt;
    @FXML
    protected TextField m_addHz;
    @FXML
    protected TextField m_addHo;
    @FXML
    protected TextField m_addWholesalePrice;
    @FXML
    protected TextField m_addRetailPrice;
    @FXML
    private Button m_go;
    @FXML
    private TextField m_horsePowerKey;
    @FXML
    private TextField m_RPMkey;
    @FXML
    private TextField m_SearchKeyField;
    @FXML
    private TableColumn<IndustryPartElement, String> m_kindColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_typeColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_kwColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_hpColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_rpmColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_voltColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_HzColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_HoColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_priceWholesaleColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_priceRetailColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_dateStoredColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_dateSoldColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_daysInStoreColumn;
    @FXML
    private TableColumn<IndustryPartElement, String> m_ButtonAttemptSell;

    protected IndustryPartElementTableController(final ObservableList<IndustryPartElement> repo)
    {
        assert null != repo : "Parameter 'repo' of method 'IndustryPartElementTableController' must not be null";
        m_repo = repo;

        //1 Wrap the observable list in a filtered list (initially display all data).
        m_filteredList = new FilteredList<>(m_repo, p -> true);
        /**
         * or the following m_filteredList.setPredicate(new Predicate<IndustryPartElement>() {
         *
         * @Override public boolean test(final IndustryPartElement next) { return true; } });
         */
        m_industryPartElementPredicate = new IndustryPartElementPredicate(m_filteredList);
    }

    protected final void initialiseSuper()
    {
        assert null != m_repo : "Class field 'm_repo' in method 'initialiseSuper' must not be null";

        updateAmounts();
        m_typeColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        m_kwColumn.setCellValueFactory(cellData -> cellData.getValue().kwProperty());
        m_hpColumn.setCellValueFactory(cellData -> cellData.getValue().hpProperty());
        m_rpmColumn.setCellValueFactory(cellData -> cellData.getValue().rpmProperty());
        m_voltColumn.setCellValueFactory(cellData -> cellData.getValue().voltProperty());
        m_HzColumn.setCellValueFactory(cellData -> cellData.getValue().HzProperty());
        m_HoColumn.setCellValueFactory(cellData -> cellData.getValue().HoProperty());
        m_priceWholesaleColumn.setCellValueFactory(cellData -> cellData.getValue().priceWholesale());
        m_priceRetailColumn.setCellValueFactory(cellData -> cellData.getValue().priceRetail());
        m_dateStoredColumn.setCellValueFactory(cellData -> cellData.getValue().dateStoredProperty());
        m_dateSoldColumn.setCellValueFactory(cellData -> cellData.getValue().dateSoldProperty());
        m_daysInStoreColumn.setCellValueFactory(cellData -> cellData.getValue().daysInStoreProperty());
        initialiseSpecific();
        m_ButtonAttemptSell.setCellFactory(new Callback<TableColumn<IndustryPartElement, String>, TableCell<IndustryPartElement, String>>()
        {
            @Override
            public TableCell<IndustryPartElement, String> call(TableColumn<IndustryPartElement, String> arg0)
            {
                return composeButtonAttemptSell();
            }
        });

        //2. Set the filter predicate whenever the filter (seek criterion) changes.
        m_SearchKeyField.textProperty().addListener(m_industryPartElementPredicate.predicateOnAllProperties());

        // 3 . Wrap the filtered_list in a sorted list
        final SortedList<IndustryPartElement> sortedList = new SortedList<>(m_filteredList);

        sortedList.comparatorProperty().bind(m_industryPartsElementTableView.comparatorProperty());
        m_industryPartsElementTableView.setItems(sortedList);

        gatherClearableInputControls();

        m_go.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(final ActionEvent actionEvent)
            {
                handleWhenGoClicked();
            }
        });

        m_add.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                m_repo.add(instantiateTargetIndustryPart());
                clearClearables();
                persist(m_repo);
                initialiseSuper();
            }
        });
    }

    protected abstract void initialiseSpecific();

    protected final void clearClearables()
    {
        if (null == m_clearableControlSet)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("m_clearableControlSet is null");
            }
        }
        else
        {
            for (final TextInputControl next : m_clearableControlSet)
            {
                if (null == next)
                {
                    LOGGER.debug("next is null");
                }
                else
                {
                    next.clear();
                }
            }
        }
    }

    protected abstract IndustryPartElement instantiateTargetIndustryPart();

    protected final void gatherClearableInputControls()
    {
        final Set<TextInputControl> tmp = new HashSet<>(10);
        add2GatherClearableInputControls(tmp);
        tmp.add(m_addName);
        tmp.add(m_addKW);
        tmp.add(m_addHP);
        tmp.add(m_addRPM);
        tmp.add(m_addVolt);
        tmp.add(m_addHz);
        tmp.add(m_addHo);
        tmp.add(m_addWholesalePrice);
        tmp.add(m_addRetailPrice);
        m_clearableControlSet = Collections.unmodifiableSet(tmp);
    }

    protected abstract void add2GatherClearableInputControls(final Set<TextInputControl> tmp);

    protected final void persist(final ObservableList<IndustryPartElement> repo)
    {
        assert null != repo : "Parameter 'repo' of method 'persist' must not be null";
        final List<String> lines = new ArrayList<>(repo.size());
        for (final IndustryPartElement next : repo)
        {
            lines.add(next.getPresentationName());
        }
        FileUtil.write(lines, deriveSpecificRepoOnDiskPointer());
    }

    protected abstract RepoOnDiskPointer deriveSpecificRepoOnDiskPointer();

    protected final TableCell<IndustryPartElement, String> composeButtonAttemptSell()
    {
        return new TableCellButton(this, Constants.SELL, new AttemptSellCommandHandler(m_industryPartsElementTableView, this));
    }

    protected final void handleWhenGoClicked()
    {
        final String horsePowerKeyString = m_horsePowerKey.textProperty().get().toLowerCase();
        final String RPMkeyString = m_RPMkey.textProperty().get().toLowerCase();
        m_industryPartElementPredicate.predicateOn(RPMkeyString, horsePowerKeyString);
    }

    @Override
    public final void valueModified(int index)
    {
        persist(m_repo);
    }

    protected final void updateAmounts()
    {
        m_inStoreIndustryPartsElementAmount.setText(String.valueOf(deriveInStoreIndustryPartsElementAmount(m_repo)));
        m_soldIndustryPartsElementAmount.setText(String.valueOf(deriveAmountOfSoldIndustryPartsElement(m_repo)));
        m_totalIndustryPartsElementAmount.setText(String.valueOf(m_repo.size()));
    }

    private int deriveInStoreIndustryPartsElementAmount(final ObservableList<IndustryPartElement> repo)
    {
        assert null != repo : "Parameter 'repo' of method 'deriveInStoreMotorsAmount' must not be null";
        int amount = 0;
        final Iterator<IndustryPartElement> iter = repo.iterator();
        while (iter.hasNext())
        {
            final IndustryPartElement next = iter.next();
            if (next.isInStore())
            {
                amount++;
            }
        }
        return amount;
    }

    private int deriveAmountOfSoldIndustryPartsElement(final ObservableList<IndustryPartElement> repo)
    {
        assert null != repo : "Parameter 'repo' of method 'getAmountOfSoldMotors' must not be null";
        int amount = 0;
        final Iterator<IndustryPartElement> iter = repo.iterator();
        while (iter.hasNext())
        {
            final IndustryPartElement nextMotor = iter.next();
            if (nextMotor.isSold())
            {
                amount++;
            }
        }
        return amount;
    }

    @Override
    public final void sold(IndustryPartElement industryPartElement)
    {
        assert null != industryPartElement : "Parameter 'industryPartElement' of method 'sold' must not be null";
        updateAmounts();
    }
}
