package sabai.anbar;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import sabai.anbar.Anbargal;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AnbarRepository extends JpaRepository<Anbargal, Long> {
	public List<Anbargal> findAll();

}
