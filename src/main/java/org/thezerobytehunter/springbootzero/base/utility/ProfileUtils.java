package org.thezerobytehunter.springbootzero.base.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileUtils {
    private final Environment environment;

    public String[] getActiveProfiles( ) {
        String[] profiles = environment.getActiveProfiles( );
        return profiles.length > 0 ? profiles : environment.getDefaultProfiles( );
    }

    public boolean isProfileActive( String profile ) {
        for ( String activeProfile : getActiveProfiles( ) ) {
            if ( activeProfile.equalsIgnoreCase( profile ) ) {
                return true;
            }
        }
        return false;
    }
}