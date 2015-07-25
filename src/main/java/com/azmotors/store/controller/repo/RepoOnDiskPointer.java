package com.azmotors.store.controller.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.foundation.StringUtil;
import com.azmotors.store.model.Constants;
import com.azmotors.store.model.GearMotor;
import com.azmotors.store.model.IndustryElementNull;
import com.azmotors.store.model.IndustryPartElement;
import com.azmotors.store.model.Motor;
import com.azmotors.store.model.Reducer;

public enum RepoOnDiskPointer
{
    MOTOR("azmotors_MOTOR.txt")
    {
        @Override
        public String point()
        {
            return getExactValue();
        }

        @Override
        public IndustryPartElement buildObject(String[] tokens)
        {
            if (null == tokens || 12 > tokens.length)
            {
                if (LOGGER.isDebugEnabled())
                {
                    LOGGER.debug("'tokens' is empty");
                }
            }
            else
            {
                IndustryPartElement nextMotor = null;
                if (tokens.length >= 12)
                {
                    String nameCode = tokens[1].split(Constants.TOKEN_INDICATES_INDUSTRY_PARTS_NAME)[0];
                    if (StringUtil.isEmpty(nameCode))
                    {
                        nameCode  = Constants.EMPTY_FAKE;
                    }
                    nextMotor = new Motor(nameCode,

                    tokens[10].split(Constants.SEMI_COLON)[0], 
                    tokens[2].split(Constants.SEMI_COLON)[0], 
                    tokens[3].split(Constants.SEMI_COLON)[0], 
                    tokens[4].split(Constants.SEMI_COLON)[0], 
                    tokens[5].split(Constants.SEMI_COLON)[0],
                    tokens[6].split(Constants.SEMI_COLON)[0], 
                    tokens[7].split(Constants.SEMI_COLON)[0], 
                    tokens[8].split(Constants.SEMI_COLON)[0], 
                    tokens[9].split(Constants.SEMI_COLON)[0]);
                    nextMotor.setDateSold(tokens[11].split(Constants.SEMI_COLON)[0]);
                }
                if (null == nextMotor || nextMotor.isEmpty())
                {
                    LOGGER.warn("one Motor object was not instantiated; 'nextMotor' variable of 'buildObject' method is null");
                    return IndustryElementNull.SINGLETON;
                }
                return nextMotor;
            }
            return IndustryElementNull.SINGLETON;
        }
    },

    REDUCER("azmotors_REDUCER.txt")
    {
        @Override
        public String point()
        {
            return getExactValue();
        }

        @Override
        public IndustryPartElement buildObject(final String[] tokens)
        {
           if (null == tokens || 13 > tokens.length)
            {
                if (LOGGER.isDebugEnabled())
                {
                    LOGGER.debug("'tokens' is empty");
                }
            }
            else
            {
                IndustryPartElement nextReducer = null;
                if (tokens.length >= 13)
                {
                    String nameCode = tokens[1].split(Constants.TOKEN_INDICATES_INDUSTRY_PARTS_NAME)[0];
                    if (StringUtil.isEmpty(nameCode))
                    {
                        nameCode  = Constants.EMPTY_FAKE;
                    }
                    nextReducer = new Reducer(nameCode,

                    tokens[11].split(Constants.SEMI_COLON)[0], 
                    tokens[2].split(Constants.SEMI_COLON)[0], 
                    tokens[3].split(Constants.SEMI_COLON)[0], 
                    tokens[4].split(Constants.SEMI_COLON)[0], 
                    tokens[5].split(Constants.SEMI_COLON)[0],
                    tokens[6].split(Constants.SEMI_COLON)[0], 
                    tokens[7].split(Constants.SEMI_COLON)[0], 
                    tokens[8].split(Constants.SEMI_COLON)[0], 
                    tokens[9].split(Constants.SEMI_COLON)[0],
                    tokens[10].split(Constants.SEMI_COLON)[0]);
                    nextReducer.setDateSold(tokens[12].split(Constants.SEMI_COLON)[0]);
                }
                if (null == nextReducer || nextReducer.isEmpty())
                {
                    LOGGER.warn("one Reducer object was not instantiated; variable 'nextReducer' of method 'buildObject' is null");
                    return IndustryElementNull.SINGLETON;
                }
                return nextReducer;
            }
            return IndustryElementNull.SINGLETON;
        }
    },

    GEAR_MOTOR("azmotors_GEARmotor.txt")
    {
        @Override
        public String point()
        {
            return getExactValue();
        }

        @Override
        public IndustryPartElement buildObject(final String[] tokens)
        {
            if (null == tokens || 13 > tokens.length)
            {
                if (LOGGER.isDebugEnabled())
                {
                    LOGGER.debug("'tokens' is empty");
                }
            }
            else
            {
                IndustryPartElement nextGearMotor = null;
                if (tokens.length >= 13)
                {
                    String nameCode = tokens[1].split(Constants.TOKEN_INDICATES_INDUSTRY_PARTS_NAME)[0];
                    if (StringUtil.isEmpty(nameCode))
                    {
                        nameCode  = Constants.EMPTY_FAKE;
                    }
                    nextGearMotor = new GearMotor(nameCode ,

                    tokens[11].split(Constants.SEMI_COLON)[0], 
                    tokens[2].split(Constants.SEMI_COLON)[0], 
                    tokens[3].split(Constants.SEMI_COLON)[0], 
                    tokens[4].split(Constants.SEMI_COLON)[0], 
                    tokens[5].split(Constants.SEMI_COLON)[0],
                    tokens[6].split(Constants.SEMI_COLON)[0], 
                    tokens[7].split(Constants.SEMI_COLON)[0], 
                    tokens[8].split(Constants.SEMI_COLON)[0], 
                    tokens[9].split(Constants.SEMI_COLON)[0],
                    tokens[10].split(Constants.SEMI_COLON)[0]);
                    nextGearMotor.setDateSold(tokens[12].split(Constants.SEMI_COLON)[0]);
                }
                if (null == nextGearMotor || nextGearMotor.isEmpty())
                {
                    LOGGER.warn("one Gear Motor object was not instantiated; variable 'nextGearMotor' of method 'buildObject' is null");
                    return IndustryElementNull.SINGLETON;
                }
                return nextGearMotor;
            }
            return IndustryElementNull.SINGLETON;
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(RepoOnDiskPointer.class);
    
    private final String m_exactValue;

    private RepoOnDiskPointer(final String exactValue)
    {
        assert null != exactValue && !exactValue.isEmpty() : "Parameter 'exactValue' of method 'RepoOnDiskPointer' must not be empty";
        m_exactValue = exactValue;
    }

    public abstract String point();

    public abstract IndustryPartElement buildObject(final String tokens []);

    public String getExactValue()
    {
        return m_exactValue;
    }
}
