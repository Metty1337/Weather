package metty1337;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import metty1337.config.AppConfig;
import metty1337.config.TestConfig;
import metty1337.exception.WeatherClientException;
import metty1337.exception.WeatherServerException;
import metty1337.service.interfaces.OpenWeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

@Slf4j
@SpringJUnitConfig(classes = {
    AppConfig.class,
    TestConfig.class,
    OpenWeatherServiceIntegrationTest.WireMockTestConfig.class,
})
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "api_key=test-api-key"
})
public class OpenWeatherServiceIntegrationTest {

  private static final String LATITUDE_PARAM = "lat";
  private static final String FIND_WEATHER_REQUEST_URI = "/data/2.5/weather";
  private static final String LONDON_LATITUDE = "51.5073219";
  private static final String LONGITUDE_PARAM = "lon";
  private static final String LONDON_LONGITUDE = "-0.1276474";
  private static final String API_KEY_PARAM = "appid";
  private static final String UNITS_PARAM = "units";
  private static final String METRIC_SYSTEM = "metric";
  private static final String TEST_API_KEY = "test-api-key";
  private static final String FIND_LOCATIONS_REQUEST_URI = "/geo/1.0/direct";
  private static final String NAME_PARAM = "q";
  private static final String LIMIT_ATTR = "limit";
  private static final String LONDON_NAME = "London";
  private static final int MAX_OF_POSSIBLE_LOCATIONS = 5;
  private static final String LONDON_LIST_RESPONSE = """
      [
          {
              "name": "London",
              "local_names": {
                  "he": "לונדון",
                  "ru": "Лондон",
                  "th": "ลอนดอน",
                  "ascii": "London",
                  "so": "London",
                  "cu": "Лондонъ",
                  "ca": "Londres",
                  "mg": "Lôndôna",
                  "gu": "લંડન",
                  "ka": "ლონდონი",
                  "fy": "Londen",
                  "kw": "Loundres",
                  "my": "လန်ဒန်မြို့",
                  "li": "Londe",
                  "sw": "London",
                  "rm": "Londra",
                  "mi": "Rānana",
                  "ku": "London",
                  "ab": "Лондон",
                  "sa": "लन्डन्",
                  "eu": "Londres",
                  "de": "London",
                  "no": "London",
                  "ko": "런던",
                  "an": "Londres",
                  "te": "లండన్",
                  "kl": "London",
                  "kv": "Лондон",
                  "fj": "Lodoni",
                  "sc": "Londra",
                  "ln": "Lóndɛlɛ",
                  "zu": "ILondon",
                  "ie": "London",
                  "fo": "London",
                  "tl": "Londres",
                  "mt": "Londra",
                  "sr": "Лондон",
                  "vi": "Luân Đôn",
                  "sv": "London",
                  "ky": "Лондон",
                  "ml": "ലണ്ടൻ",
                  "el": "Λονδίνο",
                  "vo": "London",
                  "ms": "London",
                  "ro": "Londra",
                  "pa": "ਲੰਡਨ",
                  "sk": "Londýn",
                  "lo": "ລອນດອນ",
                  "ar": "لندن",
                  "av": "Лондон",
                  "it": "Londra",
                  "es": "Londres",
                  "lt": "Londonas",
                  "co": "Londra",
                  "qu": "London",
                  "tg": "Лондон",
                  "kk": "Лондон",
                  "nv": "Tooh Dineʼé Bikin Haalʼá",
                  "sn": "London",
                  "fi": "Lontoo",
                  "ug": "لوندۇن",
                  "bh": "लंदन",
                  "mk": "Лондон",
                  "cy": "Llundain",
                  "yi": "לאנדאן",
                  "wa": "Londe",
                  "bn": "লন্ডন",
                  "ee": "London",
                  "gl": "Londres",
                  "oc": "Londres",
                  "br": "Londrez",
                  "am": "ለንደን",
                  "tr": "Londra",
                  "su": "London",
                  "mn": "Лондон",
                  "io": "London",
                  "pl": "Londyn",
                  "nl": "Londen",
                  "ny": "London",
                  "id": "London",
                  "sq": "Londra",
                  "be": "Лондан",
                  "af": "Londen",
                  "hr": "London",
                  "ht": "Lonn",
                  "tk": "London",
                  "gn": "Lóndyre",
                  "gv": "Lunnin",
                  "gd": "Lunnainn",
                  "ha": "Landan",
                  "fa": "لندن",
                  "tw": "London",
                  "ay": "London",
                  "bm": "London",
                  "lb": "London",
                  "en": "London",
                  "sl": "London",
                  "fr": "Londres",
                  "uz": "London",
                  "sm": "Lonetona",
                  "or": "ଲଣ୍ଡନ",
                  "hi": "लंदन",
                  "hu": "London",
                  "ff": "London",
                  "ig": "London",
                  "bs": "London",
                  "is": "London",
                  "jv": "London",
                  "cs": "Londýn",
                  "ga": "Londain",
                  "eo": "Londono",
                  "ia": "London",
                  "hy": "Լոնդոն",
                  "et": "London",
                  "ne": "लन्डन",
                  "az": "London",
                  "bg": "Лондон",
                  "km": "ឡុងដ៍",
                  "pt": "Londres",
                  "feature_name": "London",
                  "na": "London",
                  "lv": "Londona",
                  "cv": "Лондон",
                  "ta": "இலண்டன்",
                  "sd": "لنڊن",
                  "ps": "لندن",
                  "tt": "Лондон",
                  "se": "London",
                  "da": "London",
                  "os": "Лондон",
                  "ur": "علاقہ لندن",
                  "ba": "Лондон",
                  "yo": "Lọndọnu",
                  "to": "Lonitoni",
                  "si": "ලන්ඩන්",
                  "zh": "伦敦",
                  "bo": "ལོན་ཊོན།",
                  "ja": "ロンドン",
                  "ce": "Лондон",
                  "uk": "Лондон",
                  "mr": "लंडन",
                  "bi": "London",
                  "st": "London",
                  "om": "Landan",
                  "kn": "ಲಂಡನ್",
                  "nn": "London",
                  "sh": "London",
                  "wo": "Londar"
              },
              "lat": 51.5073219,
              "lon": -0.1276474,
              "country": "GB",
              "state": "England"
          },
          {
              "name": "City of London",
              "local_names": {
                  "uk": "Лондонське Сіті",
                  "pt": "Cidade de Londres",
                  "ko": "시티 오브 런던",
                  "ru": "Сити",
                  "ur": "لندن شہر",
                  "lt": "Londono Sitis",
                  "he": "הסיטי של לונדון",
                  "zh": "倫敦市",
                  "en": "City of London",
                  "fr": "Cité de Londres",
                  "hi": "सिटी ऑफ़ लंदन",
                  "es": "City de Londres"
              },
              "lat": 51.5156177,
              "lon": -0.0919983,
              "country": "GB",
              "state": "England"
          },
          {
              "name": "London",
              "local_names": {
                  "ka": "ლონდონი",
                  "fa": "لندن",
                  "en": "London",
                  "be": "Лондан",
                  "iu": "ᓚᓐᑕᓐ",
                  "ar": "لندن",
                  "lt": "Londonas",
                  "he": "לונדון",
                  "hy": "Լոնտոն",
                  "yi": "לאנדאן",
                  "cr": "ᓬᐊᐣᑕᐣ",
                  "th": "ลอนดอน",
                  "el": "Λόντον",
                  "ko": "런던",
                  "ga": "Londain",
                  "oj": "Baketigweyaang",
                  "ug": "لوندۇن",
                  "lv": "Landona",
                  "bn": "লন্ডন",
                  "ru": "Лондон",
                  "ja": "ロンドン",
                  "fr": "London"
              },
              "lat": 42.9832406,
              "lon": -81.243372,
              "country": "CA",
              "state": "Ontario"
          },
          {
              "name": "Chelsea",
              "local_names": {
                  "eu": "Chelsea",
                  "uk": "Челсі",
                  "sh": "Chelsea, London",
                  "it": "Chelsea",
                  "ja": "チェルシー",
                  "de": "Chelsea",
                  "id": "Chelsea, London",
                  "et": "Chelsea",
                  "es": "Chelsea",
                  "hu": "Chelsea",
                  "az": "Çelsi",
                  "no": "Chelsea",
                  "pt": "Chelsea",
                  "nl": "Chelsea",
                  "ru": "Челси",
                  "da": "Chelsea",
                  "tr": "Chelsea, Londra",
                  "fr": "Chelsea",
                  "pl": "Chelsea",
                  "zh": "車路士",
                  "ga": "Chelsea",
                  "fa": "چلسی",
                  "en": "Chelsea",
                  "ar": "تشيلسي",
                  "sk": "Chelsea",
                  "el": "Τσέλσι",
                  "vi": "Chelsea, Luân Đôn",
                  "ko": "첼시",
                  "ur": "چیلسی، لندن",
                  "he": "צ'לסי",
                  "af": "Chelsea, Londen",
                  "hi": "चेल्सी, लंदन",
                  "sv": "Chelsea, London"
              },
              "lat": 51.4875167,
              "lon": -0.1687007,
              "country": "GB",
              "state": "England"
          },
          {
              "name": "London",
              "lat": 37.1289771,
              "lon": -84.0832646,
              "country": "US",
              "state": "Kentucky"
          }
      ]
      """;
  private static final String ERROR_401_RESPONSE = """
      {
        "message": "Invalid api key"
      }
      """;
  private static final String ERROR_503_RESPONSE = """
      {
        "message": "Server unavailable"
      }
      """;
  private static final String LONDON_JSON_RESPONSE = """
      {
          "coord": {
              "lon": -0.1257,
              "lat": 51.5085
          },
          "weather": [
              {
                  "id": 803,
                  "main": "Clouds",
                  "description": "broken clouds",
                  "icon": "04d"
              }
          ],
          "base": "stations",
          "main": {
              "temp": 282.77,
              "feels_like": 281.48,
              "temp_min": 281.82,
              "temp_max": 284.31,
              "pressure": 985,
              "humidity": 93,
              "sea_level": 985,
              "grnd_level": 981
          },
          "visibility": 9000,
          "wind": {
              "speed": 2.57,
              "deg": 80
          },
          "clouds": {
              "all": 75
          },
          "dt": 1770377919,
          "sys": {
              "type": 2,
              "id": 2075535,
              "country": "GB",
              "sunrise": 1770363068,
              "sunset": 1770397082
          },
          "timezone": 0,
          "id": 2643743,
          "name": "London",
          "cod": 200
      }
      """;
  private static final int STATUS_503 = 503;
  private static final int STATUS_401 = 401;

  @RegisterExtension
  static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
      .options(WireMockConfiguration.wireMockConfig().dynamicPort())
      .build();

  @Autowired
  private OpenWeatherService openWeatherService;

  @Test
  void getWeatherByCoords_callsExternalApi_withApiKey() {
    wireMockExtension.stubFor(WireMock.get(WireMock.urlPathEqualTo(FIND_WEATHER_REQUEST_URI))
        .withQueryParam(LATITUDE_PARAM, WireMock.equalTo(LONDON_LATITUDE))
        .withQueryParam(LONGITUDE_PARAM, WireMock.equalTo(LONDON_LONGITUDE))
        .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
        .withQueryParam(UNITS_PARAM, WireMock.equalTo(METRIC_SYSTEM))
        .willReturn(WireMock.okJson(LONDON_JSON_RESPONSE
        )));

    openWeatherService.getWeatherByCoords(new BigDecimal(LONDON_LATITUDE),
        new BigDecimal(LONDON_LONGITUDE));

    wireMockExtension.verify(
        WireMock.getRequestedFor(WireMock.urlPathEqualTo(FIND_WEATHER_REQUEST_URI))
            .withQueryParam(LATITUDE_PARAM, WireMock.equalTo(LONDON_LATITUDE))
            .withQueryParam(LONGITUDE_PARAM, WireMock.equalTo(LONDON_LONGITUDE))
            .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
            .withQueryParam(UNITS_PARAM, WireMock.equalTo(METRIC_SYSTEM)));
  }

  @Test
  void getLocationByName_callsExternalApi_withApiKey() {
    wireMockExtension.stubFor(WireMock.get(WireMock.urlPathEqualTo(FIND_LOCATIONS_REQUEST_URI))
        .withQueryParam(NAME_PARAM, WireMock.equalTo(LONDON_NAME))
        .withQueryParam(LIMIT_ATTR, WireMock.equalTo(String.valueOf(MAX_OF_POSSIBLE_LOCATIONS)))
        .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
        .willReturn(WireMock.okJson(LONDON_LIST_RESPONSE)));
    openWeatherService.getLocationsByName(LONDON_NAME);

    wireMockExtension.verify(
        WireMock.getRequestedFor(WireMock.urlPathEqualTo(FIND_LOCATIONS_REQUEST_URI))
            .withQueryParam(NAME_PARAM, WireMock.equalTo(LONDON_NAME))
            .withQueryParam(LIMIT_ATTR, WireMock.equalTo(String.valueOf(MAX_OF_POSSIBLE_LOCATIONS)))
            .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
    );
  }

  @Test
  void getWeatherByCoords_when4xx_throwsWeatherClientException() {
    wireMockExtension.stubFor(
        WireMock.get(WireMock.urlPathEqualTo(FIND_WEATHER_REQUEST_URI))
            .withQueryParam(LATITUDE_PARAM, WireMock.equalTo(LONDON_LATITUDE))
            .withQueryParam(LONGITUDE_PARAM, WireMock.equalTo(LONDON_LONGITUDE))
            .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
            .withQueryParam(UNITS_PARAM, WireMock.equalTo(METRIC_SYSTEM))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(STATUS_401)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(ERROR_401_RESPONSE)));

    Assertions.assertThrows(
        WeatherClientException.class,
        () -> openWeatherService.getWeatherByCoords(
            new BigDecimal(LONDON_LATITUDE),
            new BigDecimal(LONDON_LONGITUDE)));

    wireMockExtension.verify(
        WireMock.getRequestedFor(WireMock.urlPathEqualTo(FIND_WEATHER_REQUEST_URI)));
  }

  @Test
  void getWeatherByCoords_when5xx_throwsWeatherServerException() {
    wireMockExtension.stubFor(
        WireMock.get(WireMock.urlPathEqualTo(FIND_WEATHER_REQUEST_URI))
            .withQueryParam(LATITUDE_PARAM, WireMock.equalTo(LONDON_LATITUDE))
            .withQueryParam(LONGITUDE_PARAM, WireMock.equalTo(LONDON_LONGITUDE))
            .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
            .withQueryParam(UNITS_PARAM, WireMock.equalTo(METRIC_SYSTEM))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(STATUS_503)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(ERROR_503_RESPONSE)));

    Assertions.assertThrows(
        WeatherServerException.class,
        () -> openWeatherService.getWeatherByCoords(
            new BigDecimal(LONDON_LATITUDE),
            new BigDecimal(LONDON_LONGITUDE)));

    wireMockExtension.verify(
        WireMock.getRequestedFor(WireMock.urlPathEqualTo(FIND_WEATHER_REQUEST_URI))
    );
  }

  @Test
  void getLocationsByName_when4xx_throwsWeatherClientException() {
    wireMockExtension.stubFor(
        WireMock.get(WireMock.urlPathEqualTo(FIND_LOCATIONS_REQUEST_URI))
            .withQueryParam(NAME_PARAM, WireMock.equalTo(LONDON_NAME))
            .withQueryParam(LIMIT_ATTR, WireMock.equalTo(String.valueOf(MAX_OF_POSSIBLE_LOCATIONS)))
            .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(STATUS_401)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(ERROR_401_RESPONSE)));

    Assertions.assertThrows(WeatherClientException.class,
        () -> openWeatherService.getLocationsByName(LONDON_NAME));

    wireMockExtension.verify(
        WireMock.getRequestedFor(WireMock.urlPathEqualTo(FIND_LOCATIONS_REQUEST_URI)));
  }

  @Test
  void getLocationsByName_when5xx_throwsWeatherServerException() {

    wireMockExtension.stubFor(
        WireMock.get(WireMock.urlPathEqualTo(FIND_LOCATIONS_REQUEST_URI))
            .withQueryParam(NAME_PARAM, WireMock.equalTo(LONDON_NAME))
            .withQueryParam(LIMIT_ATTR, WireMock.equalTo(String.valueOf(MAX_OF_POSSIBLE_LOCATIONS)))
            .withQueryParam(API_KEY_PARAM, WireMock.equalTo(TEST_API_KEY))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(STATUS_503)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(ERROR_503_RESPONSE)));

    Assertions.assertThrows(WeatherServerException.class,
        () -> openWeatherService.getLocationsByName(LONDON_NAME));

    wireMockExtension.verify(
        WireMock.getRequestedFor(WireMock.urlPathEqualTo(FIND_LOCATIONS_REQUEST_URI)));
  }

  @Configuration
  static class WireMockTestConfig {

    @Bean
    @Primary
    RestClient restClient() {
      return RestClient.builder()
          .baseUrl(wireMockExtension.baseUrl())
          .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
          .build();
    }
  }
}
