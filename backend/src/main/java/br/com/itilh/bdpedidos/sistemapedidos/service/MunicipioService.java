package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@Service
public class MunicipioService {
    @Autowired
    
    private MunicipioRepository repository;

    

    public List<Municipio> listarMunicipios() { return (List<Municipio>) repository.findAll(); } 
    
    public List<Municipio> listarMunicipiosPorEstadoId(BigInteger id) { return (List<Municipio>) repository.findByEstadoId(id); 
    } 
    
    public List<Municipio> listarMunicipiosPorEstadoNome(String nome) { return (List<Municipio>) repository.findByEstadoNomeIgnoreCase(nome);
     }

    public Municipio buscarMunicipioPorId(BigInteger id) throws Exception { return repository.findById(id) .orElseThrow(()-> new Exception("Id não encontrado.")); 
}

    public Municipio criarMunicipio(Municipio entity) throws Exception { try{ return repository.save(entity); }catch (Exception ex){ throw new Exception("Não foi possível criar o município." + ex.getMessage()); } 
        }

    public Municipio alterarMunicipio(BigInteger id,Municipio entity) throws Exception { try{ return repository.save(entity); }catch (Exception ex){ throw new Exception("Não foi possível alterar o município." + ex.getMessage()); } 
    }

    public String excluirMunicipio(BigInteger id) throws Exception{
    try{ 
        repository  .deleteById(id);
         return "Excluído";
    }catch (Exception ex){
        throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
    }
    }




}
