package br.com.paulo.converters;

import br.com.paulo.entities.militares.Patente;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PatenteConverter implements AttributeConverter<Patente,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Patente patente) {
        return patente.getCodigo();
    }

    @Override
    public Patente convertToEntityAttribute(Integer integer) {
        return Patente.fromCodigo(integer);
    }
}
