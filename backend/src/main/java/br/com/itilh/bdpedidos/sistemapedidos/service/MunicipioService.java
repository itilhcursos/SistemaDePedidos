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
    private MunicipioRepository repository;


    public Page<MunicipioDTO> listarMunicipios(Pageable pageable) {
        return toPageDTO(repository.findAll(pageable));
    }

    public Page<MunicipioDTO> listarMunicipiosPorEstadoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repository.findByEstadoId(id, pageable));
    }

    public Page<MunicipioDTO> listarMunicipiosPorEstadoNome(String nome, Pageable pageable) {
        return toPageDTO(repository.findByEstadoNomeIgnoreCase(nome, pageable));
    }

    public MunicipioDTO buscarMunicipioPorId(BigInteger id) throws Exception {
        return toDTO(repository.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Município", id)));
    }

    public MunicipioDTO criarMunicipio(MunicipioDTO origem) throws Exception {    
        validar(origem);
        return toDTO(repository.save(toEntity(origem)));
    }

    private void validar(MunicipioDTO origem) {
        // se já existe municipio com mesmo nome e no mesmo estado
        if(repository.existsByNomeAndEstadoId(origem.getNome(), origem.getEstadoId()))
          throw new MunicipioDuplicadoException(origem.getNome());
    }

    public MunicipioDTO alterarMunicipio(BigInteger id, MunicipioDTO origem) throws Exception {
        validar(origem);
        return toDTO(repository.save(toEntity(origem)));
    }

    public String excluirMunicipio(BigInteger id) throws Exception{
        try{ 
            repository.deleteById(id);
             return "Excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }
}
