package com.azmotors.store.controller;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import com.azmotors.store.controller.repo.RepoOnDiskPointer;
import com.azmotors.store.foundation.DataIOFromRepo;
import com.azmotors.store.foundation.DateUtil;
import com.azmotors.store.model.GearMotor;
import com.azmotors.store.model.IndustryPartElement;
import com.azmotors.store.model.RatioIndustryPartElement;

public final class GearMotorTableController extends IndustryPartElementTableController
{
    @FXML
    private TableColumn<RatioIndustryPartElement, String> m_ratioColumn;

    @FXML
    private TextField m_addRatio;

    public GearMotorTableController()
    {
        super(DataIOFromRepo.getInstance().getmRepo(RepoOnDiskPointer.GEAR_MOTOR));
    }

    @FXML
    private void initialize()
    {
        if (null != m_repo)
        {
            m_repo.clear();
        }
        m_repo = DataIOFromRepo.getInstance().getmRepo(deriveSpecificRepoOnDiskPointer());
        initialiseSuper();
    }

    @Override
    protected void initialiseSpecific()
    {
        m_ratioColumn.setCellValueFactory(cellData -> cellData.getValue().ratioProperty());
    }

    @Override
    protected IndustryPartElement instantiateTargetIndustryPart()
    {
        return new GearMotor(m_addName.getText(), DateUtil.today(), m_addKW.getText(), m_addHP.getText(), m_addRatio.getText(), m_addRPM.getText(),
                m_addVolt.getText(), m_addHz.getText(), m_addHo.getText(), m_addWholesalePrice.getText(), m_addRetailPrice.getText());
    }

    @Override
    protected void add2GatherClearableInputControls(final Set<TextInputControl> tmp)
    {
        assert null != tmp : "Parameter 'tmp' of method 'add2GatherClearableInputControls' must not be null";
        tmp.add(m_addRatio);
    }

    @Override
    protected RepoOnDiskPointer deriveSpecificRepoOnDiskPointer()
    {
        return RepoOnDiskPointer.GEAR_MOTOR;
    }
}
