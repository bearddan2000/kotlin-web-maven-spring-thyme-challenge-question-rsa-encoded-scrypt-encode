package example.model;

import kotlin.collections.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class User (
  var user :String
  , var pass :String
  , var roles: MutableCollection<out GrantedAuthority>
  , var challenge: Challenge ) : org.springframework.security.core.userdetails.User(user, pass, true, true, true, true, roles)
 , org.springframework.security.core.userdetails.UserDetails
{

  constructor(): this("username", "password", User.assignRole("USER"), Challenge(0, "1900"))

  constructor(username: String, password: String): this(username, password, User.assignRole("USER"), Challenge(0, "1900"))

  constructor(username: String, password: String, challenge: Challenge): this(username, password, User.assignRole("USER"), challenge)

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = true

  companion object Helper {
    fun assignRole(r: String): MutableCollection<GrantedAuthority>
    {
      var grantedAuthoritiesList = mutableListOf<GrantedAuthority>();
      grantedAuthoritiesList.add(SimpleGrantedAuthority("ROLE_" + r))
      return grantedAuthoritiesList;
    }
  }
}
