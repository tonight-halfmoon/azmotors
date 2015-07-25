package com.azmotors.store.foundation;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azmotors.store.model.Constants;

public final class DateUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil()
    {
        super();
    }

    /**
     * @param sourceDate0
     *            with assumption that this date is before now ^_^
     * @return
     */
    public static long calculateDaysSince(final String sourceDate0)
    {
        assert null != sourceDate0 && !sourceDate0.isEmpty() : "Parameter 'date0' of method 'calculateDaysSince' must not be empty";

        final Date now = new Date();
        try
        {
            final Date date0 = DateFormat.getDateInstance(DateFormat.DEFAULT).parse(sourceDate0);

            return (long) (now.getTime() - date0.getTime()) / (1000 * 60 * 60 * 24);
        }
        catch (ParseException e)
        {
            LOGGER.error(e.getMessage());
        }
        return (long) Constants.ZERO;
    }

    public static String today()
    {
        return DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
    }
}
