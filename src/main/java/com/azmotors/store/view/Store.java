package com.azmotors.store.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.model.Constants;

public final class Store extends Application
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Store.class);

    private final BorderPane m_rootLayout;

    public Store()
    {
        super();
        m_rootLayout = new BorderPane();
    }

    public Store(final String[] argv)
    {
        this();
        launch(argv);
    }

    @Override
    public void start(final Stage stage) throws Exception
    {
        assert null != stage : "Parameter 'stage' of method 'start' must not be null";

        stage.setTitle(Constants.TITLE_STORE);

        final Scene scene = new Scene(m_rootLayout);//, 1139, 576);
        stage.setScene(scene);

        final ObservableList<String> options = FXCollections.observableArrayList(Constants.MOTOR, Constants.GEAR_MOTOR, Constants.REDUCER);

        final ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().select(0);
        final GridPane topCenter = new GridPane();
        topCenter.setVgap(7);
        topCenter.setHgap(7);
        topCenter.setPadding(new Insets(7, 5, 5, 7));
        topCenter.add(new Label("Choose desired Industry element"), 0, 0);
        topCenter.add(comboBox, 1, 0);
        topCenter.setPrefHeight(17);
        topCenter.setMaxHeight(17);

        comboBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(final ActionEvent event)
            {
                assert null != event : "Parameter 'event' of method 'handle' must not be null";
                final String selection = (String) ((ComboBox<?>) event.getSource()).getValue();
                switch (selection)
                {
                    case Constants.REDUCER:
                        replaceCurrentSheetwithOther(new ReducerSheetView().load());
                        LOGGER.info("Reducer control board has been loaded");
                        break;
                    case Constants.GEAR_MOTOR:
                        replaceCurrentSheetwithOther(new GearMotorSheetView().load());
                        LOGGER.info("Gear Motor control board ");
                        break;
                    default:
                        replaceCurrentSheetwithOther(new MotorSheetView().load());
                        LOGGER.info("Motor control board been loaded");
                        break;
                }
            }
        });

        /**
         * By default load the Motor table/sheet
         */
        final Parent motorElementTable = new MotorSheetView().load();
        LOGGER.info("Motor control board been loaded");

        final SplitPane centerArea = new SplitPane();
        centerArea.setOrientation(Orientation.VERTICAL);
        centerArea.getItems().add(topCenter);
        centerArea.getItems().add(motorElementTable);

        m_rootLayout.setCenter(centerArea);
        m_rootLayout.setBottom(new HeapStatusHBox());

        stage.show();
    }

    private void replaceCurrentSheetwithOther(final Parent OtherPartsSheet)
    {
        ((SplitPane) m_rootLayout.getCenter()).getItems().remove(1);
        ((SplitPane) m_rootLayout.getCenter()).getItems().add(1, OtherPartsSheet);
    }
}
