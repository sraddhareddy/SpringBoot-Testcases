package com.stackroute.Service;

import com.stackroute.Muzix.Track;
import com.stackroute.exceptions.TrackNotFound;
import com.stackroute.Repository.TrackRepository;
import com.stackroute.exceptions.TrackAlreadyExists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceTest {
    Track track;
    @Mock
    TrackRepository trackRepository;
    @InjectMocks
    TrackServiceImplementation trackService;
    List<Track> list= null;
    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setId(1);
        track.setName("believer");
        track.setComment("Imaginer");
        list = new ArrayList<>();
        list.add(track);

    }
    @Test
    public void saveUserTestSuccess() throws TrackAlreadyExists{

        when(trackRepository.save((Track) any())).thenReturn(track);
        Track savedUser = trackService.saveTrack(track);
        Assert.assertEquals(track,savedUser);
        verify(trackRepository,times(1)).save(track);

    }

    @Test(expected = TrackAlreadyExists.class)
    public void saveUserTestFailure() throws TrackAlreadyExists {
        when(trackRepository.save((Track)any())).thenReturn(null);
        Track saveTrack = trackService.saveTrack(track);
        System.out.println("savedUser" + saveTrack);
        Assert.assertEquals(track,saveTrack);

       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/
    }

    @Test
    public void getAlltracks() throws TrackNotFound{

        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> userlist = trackService.getAllTracks();
        Assert.assertEquals(list,userlist);
    }
    @Test
    public void deleteTrack()throws TrackNotFound {
        trackRepository.deleteById(track.getId());
        verify(trackRepository).deleteById(anyInt());
    }

}