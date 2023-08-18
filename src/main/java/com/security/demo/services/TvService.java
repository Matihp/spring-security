package com.security.demo.services;

import com.security.demo.models.Tv;
import com.security.demo.repositories.ITvRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TvService {
    private ITvRepository iTvRepository;

    @Autowired
    public TvService(ITvRepository iTvRepository) {
        this.iTvRepository = iTvRepository;
    }
    public void crear(Tv tv){
        iTvRepository.save(tv);
    }
    public List<Tv> listar(){
        return iTvRepository.findAll();
    }
    public Optional<Tv> listarId(Long id){
        return iTvRepository.findById(id);
    }
    public  void update(Tv tv){
        iTvRepository.save(tv);
    }
    public void delete(Long id){
        iTvRepository.deleteById(id);
    }
}
