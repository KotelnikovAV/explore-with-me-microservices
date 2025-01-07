package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompilationMapper {
    @Mapping(target = "events", ignore = true)
    Compilation newCompilationDtoToCompilation(NewCompilationDto newCompilationDto);

    CompilationDto compilationToCompilationDto(Compilation compilation);

    List<CompilationDto> listCompilationToListCompilationDto(List<Compilation> compilations);
}
