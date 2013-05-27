function abrirModalDefault(classe) {
 
	jQuery("#mask").fadeIn(300);
  
	var h = jQuery(document).height();
	var w = jQuery(document).width();
 
	var fh = h / 2 - jQuery(classe).height() / 2;
	var fw = w / 2 - jQuery(classe).width() / 2;
 
	jQuery(classe).css("top", fh);
	jQuery(classe).css("left", fw);
 
	jQuery(classe).fadeIn(600);
 
	jQuery("#mask").click(function() {
		
  		jQuery("#mask").fadeOut(800);
  		jQuery(classe).fadeOut(400);
  		
 	});
 	
}

function fecharModalDefault(classe) {
		
  	jQuery("#mask").fadeOut(600);
  	jQuery(classe).fadeOut(300);
  		
}