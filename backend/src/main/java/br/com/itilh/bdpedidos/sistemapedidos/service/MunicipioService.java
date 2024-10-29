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
    private MunicipioRepository repositorio;


    public Page<MunicipioDTO> listarMunicipios(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<MunicipioDTO> listarMunicipiosPorEstadoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repositorio.findByEstadoId(id, pageable));
    }

    public Page<MunicipioDTO> listarMunicipiosPorEstadoNome(String nome, Pageable pageable) {
        return toPageDTO(repositorio.findByEstadoNomeIgnoreCase(nome, pageable));
    }

    public MunicipioDTO buscarMunicipioPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(()-> new IdInexistenteException("Município", id)));
    }

    public MunicipioDTO criarMunicipio(MunicipioDTO dto) throws Exception {    
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    private void validar(MunicipioDTO dto) {
        if(repositorio.existsByNomeAndEstadoId(dto.getNome(), dto.getEstadoId()))
          throw new MunicipioDuplicadoException(dto.getNome());
    }

    public MunicipioDTO alterarMunicipio(BigInteger id, MunicipioDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    public String excluirMunicipio(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Registro excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o registro informado." + ex.getMessage());
        }
    }
}
