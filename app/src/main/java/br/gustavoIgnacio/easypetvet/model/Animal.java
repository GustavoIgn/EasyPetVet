package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

public class Animal {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
	private String cpfDono;
	
	public Animal() {

    }

    public Animal(int id, String nome, String especie, String raca, int idade, String cpfDono) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
		this.cpfDono = cpfDono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
	
	public String getCPFDono() {
		return cpfDono;
	}
	
	public void setCPFDono(String cpfDono) {
		this.cpfDono = cpfDono;;
	}
	
    @Override
    public String toString() {
        return "Animal{id=" + id + ", nome='" + nome + "', especie='" + especie + "', raca='" + raca + "', idade=" + idade + ", dono=" + cpfDono + "}";
    }
}
