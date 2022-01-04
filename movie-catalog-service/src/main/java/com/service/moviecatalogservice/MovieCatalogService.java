package com.service.moviecatalogservice;

import com.service.moviecatalogservice.models.CatalogItem;
import com.service.moviecatalogservice.models.Movie;
import com.service.moviecatalogservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController // making it a rest controller  --> when making a req
@RequestMapping("/catalog")
public class MovieCatalogService {

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        RestTemplate restTemplate = new RestTemplate();

        List<Rating> ratings = Arrays.asList(
                new Rating("123",4),
                new Rating("5678",5)
        ); // hard coding the ratings for now

        // get details for each movie ID by calling movie info service

        return ratings.stream().map(rating ->{
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class); // get all rated movies ID
            return new CatalogItem(movie.getName(),"test",rating.getRating());
        }).collect(Collectors.toList());

        /*
        return Collections.singletonList(
                new CatalogItem("abc","test",4)
        );
         */
    }
}
