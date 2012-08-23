package com.canoepro2.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import com.canoepro2.domain.Athlete;
import com.canoepro2.domain.Band;
import com.canoepro2.domain.BandAthlete;
import com.canoepro2.domain.Boot;
import com.canoepro2.domain.Competition;
import com.canoepro2.domain.Distance;
import com.canoepro2.domain.Gender;
import com.canoepro2.domain.Oldgroup;
import com.canoepro2.domain.Race;
import com.canoepro2.domain.Racetype;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Athlete, String> getAthleteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Athlete, java.lang.String>() {
            public String convert(Athlete athlete) {
                return new StringBuilder().append(athlete.getForename()).append(' ').append(athlete.getFamilyname()).append(' ').append(athlete.getSex()).append(' ').append(athlete.getBirthdate()).toString();
            }
        };
    }

	public Converter<Integer, Athlete> getIdToAthleteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Athlete>() {
            public com.canoepro2.domain.Athlete convert(java.lang.Integer id) {
                return Athlete.findAthlete(id);
            }
        };
    }

	public Converter<String, Athlete> getStringToAthleteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Athlete>() {
            public com.canoepro2.domain.Athlete convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Athlete.class);
            }
        };
    }

	public Converter<Band, String> getBandToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Band, java.lang.String>() {
            public String convert(Band band) {
                return new StringBuilder().append(band.getBand()).append(' ').append(band.getRank()).append(' ').append(band.getFinish()).toString();
            }
        };
    }

	public Converter<Integer, Band> getIdToBandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Band>() {
            public com.canoepro2.domain.Band convert(java.lang.Integer id) {
                return Band.findBand(id);
            }
        };
    }

	public Converter<String, Band> getStringToBandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Band>() {
            public com.canoepro2.domain.Band convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Band.class);
            }
        };
    }

	public Converter<BandAthlete, String> getBandAthleteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.BandAthlete, java.lang.String>() {
            public String convert(BandAthlete bandAthlete) {
                return new StringBuilder().append(bandAthlete.getSit()).toString();
            }
        };
    }

	public Converter<Integer, BandAthlete> getIdToBandAthleteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.BandAthlete>() {
            public com.canoepro2.domain.BandAthlete convert(java.lang.Integer id) {
                return BandAthlete.findBandAthlete(id);
            }
        };
    }

	public Converter<String, BandAthlete> getStringToBandAthleteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.BandAthlete>() {
            public com.canoepro2.domain.BandAthlete convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), BandAthlete.class);
            }
        };
    }

	public Converter<Boot, String> getBootToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Boot, java.lang.String>() {
            public String convert(Boot boot) {
                return new StringBuilder().append(boot.getBoot()).toString();
            }
        };
    }

	public Converter<Integer, Boot> getIdToBootConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Boot>() {
            public com.canoepro2.domain.Boot convert(java.lang.Integer id) {
                return Boot.findBoot(id);
            }
        };
    }

	public Converter<String, Boot> getStringToBootConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Boot>() {
            public com.canoepro2.domain.Boot convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Boot.class);
            }
        };
    }

	public Converter<Competition, String> getCompetitionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Competition, java.lang.String>() {
            public String convert(Competition competition) {
                return new StringBuilder().append(competition.getCompetition()).append(' ').append(competition.getBegindate()).append(' ').append(competition.getEnddate()).toString();
            }
        };
    }

	public Converter<Integer, Competition> getIdToCompetitionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Competition>() {
            public com.canoepro2.domain.Competition convert(java.lang.Integer id) {
                return Competition.findCompetition(id);
            }
        };
    }

	public Converter<String, Competition> getStringToCompetitionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Competition>() {
            public com.canoepro2.domain.Competition convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Competition.class);
            }
        };
    }

	public Converter<Distance, String> getDistanceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Distance, java.lang.String>() {
            public String convert(Distance distance) {
                return new StringBuilder().append(distance.getDistance()).toString();
            }
        };
    }

	public Converter<Integer, Distance> getIdToDistanceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Distance>() {
            public com.canoepro2.domain.Distance convert(java.lang.Integer id) {
                return Distance.findDistance(id);
            }
        };
    }

	public Converter<String, Distance> getStringToDistanceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Distance>() {
            public com.canoepro2.domain.Distance convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Distance.class);
            }
        };
    }

	public Converter<Gender, String> getGenderToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Gender, java.lang.String>() {
            public String convert(Gender gender) {
                return new StringBuilder().append(gender.getGender()).toString();
            }
        };
    }

	public Converter<Integer, Gender> getIdToGenderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Gender>() {
            public com.canoepro2.domain.Gender convert(java.lang.Integer id) {
                return Gender.findGender(id);
            }
        };
    }

	public Converter<String, Gender> getStringToGenderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Gender>() {
            public com.canoepro2.domain.Gender convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Gender.class);
            }
        };
    }

	public Converter<Oldgroup, String> getOldgroupToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Oldgroup, java.lang.String>() {
            public String convert(Oldgroup oldgroup) {
                return new StringBuilder().append(oldgroup.getOldmin()).append(' ').append(oldgroup.getOldmax()).toString();
            }
        };
    }

	public Converter<Integer, Oldgroup> getIdToOldgroupConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Oldgroup>() {
            public com.canoepro2.domain.Oldgroup convert(java.lang.Integer id) {
                return Oldgroup.findOldgroup(id);
            }
        };
    }

	public Converter<String, Oldgroup> getStringToOldgroupConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Oldgroup>() {
            public com.canoepro2.domain.Oldgroup convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Oldgroup.class);
            }
        };
    }

	public Converter<Race, String> getRaceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Race, java.lang.String>() {
            public String convert(Race race) {
                return new StringBuilder().append(race.getStart()).toString();
            }
        };
    }

	public Converter<Integer, Race> getIdToRaceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Race>() {
            public com.canoepro2.domain.Race convert(java.lang.Integer id) {
                return Race.findRace(id);
            }
        };
    }

	public Converter<String, Race> getStringToRaceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Race>() {
            public com.canoepro2.domain.Race convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Race.class);
            }
        };
    }

	public Converter<Racetype, String> getRacetypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.canoepro2.domain.Racetype, java.lang.String>() {
            public String convert(Racetype racetype) {
                return new StringBuilder().append(racetype.getRacetype()).toString();
            }
        };
    }

	public Converter<Integer, Racetype> getIdToRacetypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.canoepro2.domain.Racetype>() {
            public com.canoepro2.domain.Racetype convert(java.lang.Integer id) {
                return Racetype.findRacetype(id);
            }
        };
    }

	public Converter<String, Racetype> getStringToRacetypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.canoepro2.domain.Racetype>() {
            public com.canoepro2.domain.Racetype convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Racetype.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAthleteToStringConverter());
        registry.addConverter(getIdToAthleteConverter());
        registry.addConverter(getStringToAthleteConverter());
        registry.addConverter(getBandToStringConverter());
        registry.addConverter(getIdToBandConverter());
        registry.addConverter(getStringToBandConverter());
        registry.addConverter(getBandAthleteToStringConverter());
        registry.addConverter(getIdToBandAthleteConverter());
        registry.addConverter(getStringToBandAthleteConverter());
        registry.addConverter(getBootToStringConverter());
        registry.addConverter(getIdToBootConverter());
        registry.addConverter(getStringToBootConverter());
        registry.addConverter(getCompetitionToStringConverter());
        registry.addConverter(getIdToCompetitionConverter());
        registry.addConverter(getStringToCompetitionConverter());
        registry.addConverter(getDistanceToStringConverter());
        registry.addConverter(getIdToDistanceConverter());
        registry.addConverter(getStringToDistanceConverter());
        registry.addConverter(getGenderToStringConverter());
        registry.addConverter(getIdToGenderConverter());
        registry.addConverter(getStringToGenderConverter());
        registry.addConverter(getOldgroupToStringConverter());
        registry.addConverter(getIdToOldgroupConverter());
        registry.addConverter(getStringToOldgroupConverter());
        registry.addConverter(getRaceToStringConverter());
        registry.addConverter(getIdToRaceConverter());
        registry.addConverter(getStringToRaceConverter());
        registry.addConverter(getRacetypeToStringConverter());
        registry.addConverter(getIdToRacetypeConverter());
        registry.addConverter(getStringToRacetypeConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
