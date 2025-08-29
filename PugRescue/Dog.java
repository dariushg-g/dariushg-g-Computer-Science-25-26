public class Dog {

	private String name;
	private String breed;
	
	public Dog(String name, String breed) {
		this.name = name;
		this.breed = breed;
	}
	
	public Dog(String name) {
		this(name, "Pug");
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public boolean equals(Dog other) {
		return this.name.equals(other.getName()) 
				&& this.breed.equals(other.getBreed());
	}

	@Override
	public String toString() {
		return "Dog [name=" + name + ", breed=" + breed + "]";
	}


	
	
	
}
