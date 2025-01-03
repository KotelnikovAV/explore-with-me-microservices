package ru.practicum.fallback.compilation;

import ru.practicum.client.compilation.CompilationClient;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.exception.ServerUnavailableException;

import java.util.List;

public class CompilationFallback implements CompilationClient {
    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/compilations method POST is unavailable");
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/compilations/{compId} method PATCH is unavailable");
    }

    @Override
    public void deleteCompilation(Long compId) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/compilations/{compId} method DELETE is unavailable");
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        throw new ServerUnavailableException("Endpoint /api/v1/compilations method GET is unavailable");
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        throw new ServerUnavailableException("Endpoint /api/v1/compilations/{compId} method GET is unavailable");
    }
}
