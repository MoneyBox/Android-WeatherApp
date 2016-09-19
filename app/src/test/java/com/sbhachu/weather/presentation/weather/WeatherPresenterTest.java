package com.sbhachu.weather.presentation.weather;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.LatLong;
import com.sbhachu.weather.model.domain.City;
import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.domain.Forecast;
import com.sbhachu.weather.model.domain.ForecastData;
import com.sbhachu.weather.model.domain.Weather;
import com.sbhachu.weather.model.domain.WeatherData;
import com.sbhachu.weather.model.interactor.ICurrentWeatherInteractor;
import com.sbhachu.weather.model.interactor.IDailyWeatherInteractor;
import com.sbhachu.weather.model.interactor.ILocationInteractor;
import com.sbhachu.weather.model.interactor.IReverseGeocodeInteractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    @Mock
    private ILocationInteractor locationInteractor;

    @Mock
    private IReverseGeocodeInteractor reverseGeocodeInteractor;

    @Mock
    private ICurrentWeatherInteractor currentWeatherInteractor;

    @Mock
    private IDailyWeatherInteractor dailyWeatherInteractor;

    @Mock
    IWeatherView view;

    private WeatherPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new WeatherPresenter(locationInteractor, reverseGeocodeInteractor, currentWeatherInteractor, dailyWeatherInteractor);
        presenter.attachView(view);
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
        presenter = null;
    }

    @Test
    public void testRequestUserLocationOnStart() {
        presenter.start();
        verify(locationInteractor, times(1)).resume(presenter);
    }

    @Test
    public void testPauseRequestingLocationOnStop() {
        presenter.stop();
        verify(locationInteractor, times(1)).pause();
    }

    @Test
    public void testFetchWeatherData() {
        presenter.fetchWeatherData("London", "GB");
        verify(currentWeatherInteractor, times(1)).getCurrentWeather("London", "GB", presenter);
        verify(dailyWeatherInteractor, times(1)).getDailyWeather("London", "GB", presenter);
    }

    @Test
    public void testForceRefreshNoLocation() {
        given(locationInteractor.getLastLatLong()).willReturn(null);
        presenter.forceRefresh();
        verify(reverseGeocodeInteractor, never()).resolveAddress(0d, 0d, presenter);
    }

    @Test
    public void testForceRefreshWithLocation() {
        given(locationInteractor.getLastLatLong()).willReturn(new LatLong(0d, 0d));
        presenter.forceRefresh();
        verify(reverseGeocodeInteractor, times(1)).resolveAddress(0d, 0d, presenter);
    }

    @Test
    public void testOnLocationChanged() {
        doAnswer(new LocationInteractorAnswer() {
            @Override
            void answer(ILocationInteractor.Actions listener) {
                listener.onLocationChanged(0.0d, 0.0d);
            }
        }).when(locationInteractor).resume(presenter);

        locationInteractor.resume(presenter);
        verify(reverseGeocodeInteractor).resolveAddress(0.0d, 0.0d, presenter);
    }

    @Test
    public void testOnAddressResolved() {
        doAnswer(new ReverseGeocodeInteractorAnswer() {
            @Override
            void answer(IReverseGeocodeInteractor.Actions listener) {
                listener.onAddressResolved("London", "GB");
            }
        }).when(reverseGeocodeInteractor).resolveAddress(0d, 0d, presenter);

        reverseGeocodeInteractor.resolveAddress(0d, 0d, presenter);
        verify(locationInteractor, times(1)).pause();
    }

    @Test
    public void testFetchCurrentWeatherSuccessInvalidData() {
        doAnswer(new CurrentWeatherInteractorAnswer() {
            @Override
            void answer(ICurrentWeatherInteractor.Listener listener) {
                listener.onCurrentWeatherSuccess(new CurrentWeather(null, null, null));
            }
        }).when(currentWeatherInteractor).getCurrentWeather("London", "GB", presenter);

        currentWeatherInteractor.getCurrentWeather("London", "GB", presenter);
        verify(view, times(1)).showError(R.string.invalid_data_error_title, R.string.invalid_data_error_body);
    }

    @Test
    public void testFetchCurrentWeatherSuccess() {

        final CurrentWeather data = new CurrentWeather(new ArrayList<Weather>(), new WeatherData(), "test");
        final DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
        final String currentDate = formatter.format(new Date());

        doAnswer(new CurrentWeatherInteractorAnswer() {
            @Override
            void answer(ICurrentWeatherInteractor.Listener listener) {
                listener.onCurrentWeatherSuccess(data);
            }
        }).when(currentWeatherInteractor).getCurrentWeather("London", "GB", presenter);

        currentWeatherInteractor.getCurrentWeather("London", "GB", presenter);

        verify(view, times(1)).updateCurrentWeather(data);
        verify(view, times(1)).updateDate(currentDate);
    }

    @Test
    public void testFetchCurrentWeatherFailure() {
        doAnswer(new CurrentWeatherInteractorAnswer() {
            @Override
            void answer(ICurrentWeatherInteractor.Listener listener) {
                listener.onCurrentWeatherFailure();
            }
        }).when(currentWeatherInteractor).getCurrentWeather("London", "GB", presenter);

        currentWeatherInteractor.getCurrentWeather("London", "GB", presenter);
        verify(view, times(1)).showError(R.string.error_title, R.string.error_body);
    }

    @Test
    public void testFetchDailyWeatherSuccess() {
        final Forecast data = new Forecast(new City(), new ArrayList<ForecastData>());

        doAnswer(new DailyWeatherInteractorAnswer() {
            @Override
            void answer(IDailyWeatherInteractor.Listener listener) {
                listener.onDailyWeatherSuccess(data);
            }
        }).when(currentWeatherInteractor).getCurrentWeather("London", "GB", presenter);

        currentWeatherInteractor.getCurrentWeather("London", "GB", presenter);
        verify(view, times(1)).updateDailyWeather(data);
    }

    @Test
    public void testFetchDailyWeatherFailure() {
        doAnswer(new DailyWeatherInteractorAnswer() {
            @Override
            void answer(IDailyWeatherInteractor.Listener listener) {
                listener.onDailyWeatherFailure();
            }
        }).when(dailyWeatherInteractor).getDailyWeather("London", "GB", presenter);

        dailyWeatherInteractor.getDailyWeather("London", "GB", presenter);
        verify(view, times(1)).showError(R.string.error_title, R.string.error_body);
    }

    private abstract static class LocationInteractorAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            ILocationInteractor.Actions locationInteractorListener = (ILocationInteractor.Actions) invocation.getArguments()[0];
            answer(locationInteractorListener);
            return null;
        }

        abstract void answer(ILocationInteractor.Actions listener);
    }

    private abstract static class ReverseGeocodeInteractorAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            IReverseGeocodeInteractor.Actions reverseGeocodeInteractorListener = (IReverseGeocodeInteractor.Actions) invocation.getArguments()[2];
            answer(reverseGeocodeInteractorListener);
            return null;
        }

        abstract void answer(IReverseGeocodeInteractor.Actions listener);
    }

    private abstract static class CurrentWeatherInteractorAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            ICurrentWeatherInteractor.Listener currentWeatherInteractorListener = (ICurrentWeatherInteractor.Listener) invocation.getArguments()[2];
            answer(currentWeatherInteractorListener);
            return null;
        }

        abstract void answer(ICurrentWeatherInteractor.Listener listener);
    }

    private abstract static class DailyWeatherInteractorAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            IDailyWeatherInteractor.Listener dailyWeatherInteractorListener = (IDailyWeatherInteractor.Listener) invocation.getArguments()[2];
            answer(dailyWeatherInteractorListener);
            return null;
        }

        abstract void answer(IDailyWeatherInteractor.Listener listener);
    }
}