package it.f2informatica.core.gateway;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public abstract class EntityToModelConverter<S, T> implements Converter<S, T> {

  public List<T> convertList(List<S> sourceList) {
    return FluentIterable.from(convertIterable(sourceList)).toList();
  }

  public Iterable<T> convertIterable(Iterable<S> sourceIterable) {
    return Iterables.transform(sourceIterable,
      new Function<S, T>() {
        @Override
        public T apply(S source) {
          return convert(source);
        }
      }
    );
  }

}
