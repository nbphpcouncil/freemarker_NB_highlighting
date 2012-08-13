<@_sites.connectLink site=site userId=loginUserId age=MAX_AGE /><#import "*/macros/sites.ftl" as _sites/>
  <#assign MAX_AGE = 1000 />
 
  <#-- many lines later ... -->
  
  
  <td class="narrow"><@_sites.sessionStatus site=site/></td>
  <#if !loginUser.isVendorUser >
    <td class="narrow">
      <a href="<@s.url action="viewapplication" siteId=site.siteId />">
        <@s.text name="listapplications.actions.view"/>
      </a>
    </td>
  </#if>
  <td class="narrow">
    <@_sites.connectLink site=site userId=loginUserId age=MAX_AGE />
  </td>