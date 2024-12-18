package com.coder.shopeasy.repository;

import com.coder.shopeasy.model.Category;
import com.coder.shopeasy.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
