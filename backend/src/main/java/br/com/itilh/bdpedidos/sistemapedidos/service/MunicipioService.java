package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.MunicipioDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@Service
public class MunicipioService extends GenericService<Municipio, MunicipioDTO>{

    @Autowired
    private MunicipioRepository municipioRepository;

// LISTAGENS (GET) //

    // Listar todos os municípios
    public Page<MunicipioDTO> listarMunicipios(Pageable pageable) {
        return toPageDTO(municipioRepository.findAll(pageable));
    }

    // Listar os municípios pelo texto
    public Page<MunicipioDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(municipioRepository.findByNomeContainingIgnoreCase(pageable, txtBusca));
    }

    // Listar municípios pelo ID do estado
    public Page<MunicipioDTO> listarMunicipiosPorEstadoId(BigInteger id, Pageable pageable) {
        return toPageDTO(municipioRepository.findByEstadoId(id, pageable));
    }

    // Listar municípios pelo nome do estado
    public Page<MunicipioDTO> listarMunicipiosPorEstadoNome(String nome, Pageable pageable) {
        return toPageDTO(municipioRepository.findByEstadoNomeIgnoreCase(nome, pageable));
    }

    // Listar município pelo ID
    public MunicipioDTO buscarMunicipioPorId(BigInteger id) throws Exception {
        return toDTO(municipioRepository.findById(id).orElseThrow(() -> new IdInexistenteException("Município", id)));
    }

// POST - PUT - DELETE //

    // Criar registro de município
    public MunicipioDTO criarMunicipio(MunicipioDTO origem) throws Exception {    
        validar(origem);
        return toDTO(municipioRepository.save(toEntity(origem)));
    }

    // Atualizar registro de município
    public MunicipioDTO alterarMunicipio(BigInteger id, MunicipioDTO origem) throws Exception {
        validar(origem);
        return toDTO(municipioRepository.save(toEntity(origem)));
    }

    // Excluir registro de município 
    public String excluirMunicipio(BigInteger id) throws Exception{
        try{ 
            municipioRepository.deleteById(id);
             return "Excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }
    
// VALIDAÇÕES //

    // Validação de duplicidade de município
    private void validar(MunicipioDTO dto) throws Exception {
        if (municipioRepository.existsByNomeAndEstadoId(dto.getNome(), dto.getEstadoId())) {
            if (dto.getId() == null) {
                throw new MunicipioDuplicadoException(dto.getNome());
            } else {
                Municipio m = municipioRepository.getReferenceById(dto.getId());
                if (!m.getNome().equalsIgnoreCase(dto.getNome())) {
                    throw new MunicipioDuplicadoException(dto.getNome());
                }
            }
        }

    }
}
