const formatoDMA = (dataAMD) => {
   const dia =  dataAMD.substring(8,10);
   const mes =  dataAMD.substring(5,7);
   const ano = dataAMD.substring(0,4);
   return  dia + "/"+ mes +"/"+ ano;
};

 export default{
    formatoDMA
 }
 