package br.digivox.odravison.apiserver.shared.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapperDomainUtil {

    protected static final ModelMapper MODEL_MAPPER = buildModelMapper();

    protected static ModelMapper buildModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    public static <S, D> D mapTo(S source, Class<D> destClass) {
        return MODEL_MAPPER.map(source, destClass);
    }

    public static <D, S> List<D> toList(List<S> items, Class<D> destClass) {
        List<D> listItemsDTO = new ArrayList<>();

        if (items != null) {
            items.forEach(item -> listItemsDTO.add(mapTo(item, destClass)));
        }
        return listItemsDTO;
    }

    public static <D, S> Set<D> toSet(Set<S> items, Class<D> destClass) {
        Set<D> setItemsDTO = new HashSet<>();

        if (items != null) {
            items.forEach(item -> setItemsDTO.add(mapTo(item, destClass)));
        }
        return setItemsDTO;
    }
}
