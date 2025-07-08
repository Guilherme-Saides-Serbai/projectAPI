package redeSocial.Config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JsonbConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null ? attribute : "{}";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData != null ? dbData : "{}";
    }
}