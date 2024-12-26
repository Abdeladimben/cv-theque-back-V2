package com.api.cv.repositories.offer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.api.cv.dto.offer.OfferSearchRequestDto;
import com.api.cv.entities.offer.Offer;

import jakarta.persistence.criteria.Predicate;

public class OfferSpecification {

    public static Specification<Offer> filterOffers(OfferSearchRequestDto offerSearchRequestDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Title filtering
            if (offerSearchRequestDto.getTitle() != null && !offerSearchRequestDto.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + offerSearchRequestDto.getTitle().toLowerCase() + "%"
                ));
            }

            // Description filtering
            if (offerSearchRequestDto.getDescription() != null && !offerSearchRequestDto.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    "%" + offerSearchRequestDto.getDescription().toLowerCase() + "%"
                ));
            }

            // Post filtering
            if (offerSearchRequestDto.getPost() != null && !offerSearchRequestDto.getPost().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("post")),
                    "%" + offerSearchRequestDto.getPost().toLowerCase() + "%"
                ));
            }

            // Ville filtering
            if (offerSearchRequestDto.getVille() != null && !offerSearchRequestDto.getVille().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("ville")),
                    "%" + offerSearchRequestDto.getVille().toLowerCase() + "%"
                ));
            }

            // Remuneration filtering
            if (offerSearchRequestDto.getRemuneration() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("remuneration"), offerSearchRequestDto.getRemuneration()
                ));
            }

            // DureeContrat filtering
            if (offerSearchRequestDto.getDureeContrat() > 0) {
                predicates.add(criteriaBuilder.equal(
                    root.get("dureeContrat"), offerSearchRequestDto.getDureeContrat()
                ));
            }

            // Creation date filtering (min)
            if (offerSearchRequestDto.getCreationDateMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("createDateTime"), offerSearchRequestDto.getCreationDateMinAsDateTime()
                ));
            }

            // Creation date filtering (max)
            if (offerSearchRequestDto.getCreationDateMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("createDateTime"), offerSearchRequestDto.getCreationDateMaxAsDateTime()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
