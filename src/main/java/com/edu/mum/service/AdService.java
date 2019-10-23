package com.edu.mum.service;

import com.edu.mum.domain.Ad;
import java.util.List;

public interface AdService {

    Ad create(Ad ad);
    void delete(Ad ad);
    List<Ad> findAllAds();

    Ad save(Ad ad);
}
