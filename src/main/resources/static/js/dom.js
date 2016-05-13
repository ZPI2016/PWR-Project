/**
 * Created by aman on 29.04.16.
 */

$(document).on("click", 'ul li', function(){
    $('ul li').removeClass('active');
    $(this).addClass('active');
});