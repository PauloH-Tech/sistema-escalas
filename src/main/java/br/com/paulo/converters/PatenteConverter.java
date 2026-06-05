package br.com.paulo.converters;

import br.com.paulo.entities.militares.Graduacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PatenteConverter implements AttributeConverter<Graduacao,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Graduacao graduacao) {
        return graduacao.getCodigo();
    }

    @Override
    public Graduacao convertToEntityAttribute(Integer integer) {
        return Graduacao.fromCodigo(integer);
    }
}
