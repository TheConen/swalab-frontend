package com.swalab.frontend.converter;

import javafx.util.StringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter extends StringConverter<Date> {
    private DateFormat _dateFormat;

    public DateConverter(){
        _dateFormat=DateFormat.getDateInstance();
    }

    @Override
    public String toString(Date date) {
        if(date==null){
            return "";
        }
        return _dateFormat.format(date);
    }

    @Override
    public Date fromString(String s) {
        Date parse = null;
        try {
            parse = _dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
