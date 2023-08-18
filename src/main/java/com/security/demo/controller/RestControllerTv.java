package com.security.demo.controller;

import com.security.demo.models.Tv;
import com.security.demo.services.TvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tv/")
public class RestControllerTv {
    private TvService tvService;

    @Autowired
    public RestControllerTv(TvService tvService) {
        this.tvService = tvService;
    }
    @PostMapping(value = "crear",headers = "Accept=application/json")
    public void crearTv(@RequestBody Tv tv){
        tvService.crear(tv);
    }
    @GetMapping(value = "listar",headers = "Accept=application/json")
    public List<Tv> listarTvs(){
        return tvService.listar();
    }
    @GetMapping(value = "listarId/{id}",headers = "Accept=application/json")
    public Optional<Tv> listarId(@PathVariable Long id){
        return tvService.listarId(id);
    }
    @PutMapping(value = "actualizar",headers = "Accept=application/json")
    public void modificarTv(@RequestBody Tv tv){
        tvService.update(tv);
    }
    @DeleteMapping(value = "eliminar/{id}",headers = "Accept=application/json")
    public void eliminarTv(@PathVariable Long id){
        tvService.delete(id);
    }
}
