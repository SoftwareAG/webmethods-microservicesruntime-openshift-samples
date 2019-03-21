<HTML>

<HEAD>
  <META http-equiv="Pragma" content="no-cache">
  <META http-equiv='content-type' content='text/html; charset=UTF-8'>
  <META http-equiv="Expires" content="-1">
  <LINK rel="stylesheet" type="text/css" href="reiff_esb.css">
</HEAD>


<SCRIPT>
  function launchHelp()
  {
    if(parent.menu != null){
       window.open(parent.menu.document.forms["urlsaver"].helpURL.value, 'help', "directories=no,location=yes,menubar=yes,scrollbars=yes,status=yes,toolbar=yes,resizable=yes", TRue);
    }
  }
  
  function loadPage(url)
  {
    window.location.replace(url);
  }

  %ifvar message%
    %ifvar norefresh%%else%
      setTimeout("loadPage('top.dsp')", 30000);
    %endif%
  %endif%
  
</SCRIPT>

<BODY class="topbar" topmargin="0" leftmargin="0" marginwidth="0" marginheight="0">

<TABLE border=0 cellspacing=0 cellpadding=0 height=100% width=100%>
  <TR>
    <TD>

      <TABLE width=100% cellspacing=0 border=0>
        <TR>
          <TD nowrap class="toptitle" width="100%">
          
             %value $host%
             ::
             Integration Server%ifvar text%
             ::
             %value text%%endif%
          </TD>
          <TD bgcolor="FFFFFF">
            <IMG src="./images/newlogo.gif" border=0>
          </TD>
        </TR>
      </TABLE>
      
    </TD>
  </TR>
  <TR height=100%>
    <TD>
      <TABLE width=100% height=100% cellspacing=0 border=0>
        <TR>
           %ifvar adapter%
           
           %else%
             %invoke wm.server.query:getKeyInfo%
               %ifvar keyExpired%
                 <TD width=100%><center><A class="keymessage"
                   href="settings-license.dsp" TARGET="body">License Key is Expired or Invalid.</A></center>
                 </TD>
               %else%
                 %ifvar keyExpiresIn%
                   <TD width=100%><center>&nbsp;<A class="keymessage" href="settings-license.dsp" TARGET="body">
                     %ifvar keyExpiresIn equals('0')%
                       License Key expires today.
                     %else%
                       License Key expires in about %value keyExpiresIn% days
                     %endif%</A></center>
                   </TD>
                 %else%
                   <TD nowrap width=100% class="topmenu">&nbsp;
                   </TD>
                 %endif%
               %endif%
             %endinvoke%
           %endif%
           
           <TD nowrap valign="bottom" class="topmenu">
               %ifvar adapter%
                 <A href='javascript:window.parent.close();'>Close Window</A>
               %endif%
               %ifvar help%| 
                 <A TARGET='adapter-body' onclick="launchHelp();return false;" href='#'>Help</A>
               %endif%
               %else%
                 <A  href='javascript:window.parent.close();'>Close Window</A>
                %endif%
            </TD>

         </TR>

         <TR>

         </TR>

      </TABLE>

</TD>
</TR>
</TABLE>


</BODY>
</HTML>

