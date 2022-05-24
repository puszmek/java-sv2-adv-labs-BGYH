package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTest {

     @Mock
     LocationsService locationsService;

     @InjectMocks
     LocationsController locationsController;

    @Test
    void testGetLocations() {
        List<Location> locations = Arrays.asList(
                new Location("First Location", 35.142536, 36.748596),
                new Location("Second Location", 37.142536, 45.635241)
        );
        when(locationsService.getLocations()).thenReturn(locations);

        String result = locationsController.getLocations();

        assertThat(result).contains("First Location", "Second Location");
    }
}