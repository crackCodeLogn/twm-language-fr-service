package com.vv.personal.twm.language.fr.data.repository;

import com.vv.personal.twm.language.fr.data.entity.LanguageFrEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Vivek
 * @since 2025-05-17
 */
@Repository
public interface LanguageFrRepository extends JpaRepository<LanguageFrEntity, UUID> {
  boolean existsByFrenchWord(String frenchWord);

  Optional<LanguageFrEntity> findByFrenchWord(String frenchWord);

  @Query(value = "select * from language_fr where lower(en) ~ :englishWord", nativeQuery = true)
  List<LanguageFrEntity> findFrenchWordsContainingWordInMeaning(String englishWord);

  @Query(value = "select * from language_fr where pos(en) ~ :posTag", nativeQuery = true)
  List<LanguageFrEntity> findFrenchWordsMatchingPos(String posTag);

  @Query(value = "select * from language_fr where gender ~ :gender", nativeQuery = true)
  List<LanguageFrEntity> findFrenchWordsMatchingGender(String gender);
}
