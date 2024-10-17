package br.com.itilh.bdpedidos.sistemapedidos.model;

public enum UsuarioRole {
    ADMIN("Admin"),
    USER("User");

    private String role;
    UsuarioRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
