package net.octopusstudios.carnospace.cmp.greendao.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Ok, i've stolen this one...
 * Created by Davide on 17/03/2017.
 */

public class StringListConverter implements PropertyConverter<List, String> {

    public static final String SEPARATOR = ";---;";

    @Override
    public List convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        else {
            List list = Arrays.asList(databaseValue.split(SEPARATOR));
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List entityProperty) {
        if(entityProperty==null){
            return null;
        }
        else{
            StringBuilder sb= new StringBuilder();
            for(Object link : entityProperty){
                sb.append(link);
                sb.append(SEPARATOR);
            }
            return sb.toString();
        }
    }
}