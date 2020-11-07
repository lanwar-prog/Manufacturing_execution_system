<%-- 
    Document   : AddOrder.jsp
    Created on : 04.04.2016, 15:58:57
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="styles/jquery.min.1.4.2.js"></script>
        <script src="styles/ctrl.js"></script>
                 <%! String pgname = "Заказы";%>
        <title><%= pgname%></title>
   
        <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
        <link rel="stylesheet" type="text/css" href="styles/table.css">
        <link rel="stylesheet" type="text/css" href="styles/popup2.css">
 <style>
div.popup-credit{
  
  display:none;
}
a.cancelComment {
  cursor:pointer;
  float:inherit;
   
  margin:0px 0px;
}
</style>

        
    </head>
    <body bgcolor="DCDCDA">
     <div class="Divbg bg2"></div>  
     <div class="DivbgInner">

        <%            
            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
        
        <%@include file="HeadSapcon.jsp"%>

        <table align="center">
            <tr>
                <td colspan="2">
                    <table align="center">
                        <tr>
                            <td colspan="2"> <%@include file="menuHead.jsp"%>&nbsp;</td>
                        </tr>	
                    </table>
                </td>
            </tr>
            <!--<tr>
                <td colspan="2" align="center">
                    <div id="msg-box">
                        <div id="msg">
                        </div>
                    </div>
                </td>
            </tr>-->
            <tr valign="top">
                <td style="padding: 23px 30px 0px 30px;">
                    <a class="pp-credit-block-button" id="linkList" href="#" onclick="return false">Список заказов</a>
                    <div class="popup-credit" id="popup-list">
                        <div id="listid" style="margin-top: 0px;">
                        </div>
                    </div>
                    <br>
                    <a class="pp-credit-block-button" id="linkList2" href="#" onclick="return false">Список заказов в процессе выполнения</a>
                    <div class="popup-credit" id="popup-list2">
                        
                    </div>
                    <br>
                    <a class="pp-credit-block-button" id="linkList3" href="#" onclick="return false">Список выполненных заказов</a>
                    <div class="popup-credit" id="popup-list3">
                        
                    </div>
                </td>
                <td>                   
                    <div id="formid">
                    </div>
                </td>
            </tr>
        </table>
     </div>
        <script>
            $(document).ready(function() {
                $('#linkList').live('click', function(){
                    $('#popup-list').slideToggle('slow');
                });
                $('#linkList2').live('click', function(){
                    $('#popup-list2').slideToggle();
                });
                $('#linkList3').live('click', function(){
                    $('#popup-list3').slideToggle();
                });
                
                $('#listid').live("DOMSubtreeModified", function(){
                    $('#popup-list2').load('${pageContext.request.contextPath}/OrderContr?action=list2');
                    $('#popup-list3').load('${pageContext.request.contextPath}/OrderContr?action=list3');
                });
                
                getData(0,"${pageContext.request.contextPath}/OrderContr","list");
                getData(1,"${pageContext.request.contextPath}/OrderContr","clear");
            });
        </script>
    </body>
</html>
