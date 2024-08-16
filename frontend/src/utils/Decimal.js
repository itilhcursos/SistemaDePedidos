
const toBD = (valor) => {
   let texto = valor.toString();
   return texto.replace(".","").replace(",",".").replace("R$","");
};

const toTela = (valor) => {
    let texto = String(valor.toFixed(2));
    texto = texto.replace(",","").replace(".",",").replace("R$","");
    return  texto;
};

export default{
    toBD,
    toTela
}