const baseURL = "http://localhost:8080/";
const frontURL = "http://localhost:63342/tft-api/frontend/static/img/";
const listaBox = $("#lista-data");
const detalheBox = $("#detalhe-data");
const dadoSelecionado = $("#lista-info");
let Dados = {};
let Detalhe = {
    unidades: function (obj) {
        let saida = "<span>"+obj.cost+"</span>" +
                    "<div id='sinergias'>" +
                        "<h3> Sinergias </h3>";

        for(let s of obj.traits) {
            let nome = s.split("_")[1];
            saida +=    "<div id='"+nome+"' onclick='alterarDetalhe(this.id, \"sinergias\", \"traits/\", \"name\")'>" +
                            "<h5> " + nome + " </h5>" +
                            "<img width='60px' src='"+frontURL+"/traits/"+nome+".png'/>" +
                         "</div>";
        }

        saida +=    "</div>";
        return saida;
    },
    sinergias: function (obj) {
        let saida = "<span>Tipo: "+obj.type+"</span>" +
                    "<p>"+obj.description+"</p>";

        if(obj.innate) saida += "<p>"+obj.innate+"</p>";

        saida +=    "<div>"
        for(let s of obj.sets){
            saida += "<p><b>Estilo: "+s.style+"</b> quando possuir no mínimo "+s.min+" Campeões "+obj.name+"</p>";
        }
        saida +=    "</div>" +
                    "<div id='campeoes'>" +
                        "<h3> Campeões </h3>";
        for(let c of obj.unidades){
            let nome = c.split("_")[1];
            saida +=    "<div id='"+c+"' onclick='alterarDetalhe(this.id, \"unidades\", \"champions/\", \"championId\")'>" +
                            "<h5> " + nome + " </h5>" +
                            "<img width='60px' src='"+frontURL+"/champions/"+c+".png'/>" +
                        "</div>";
        }
        saida +=    "</div>";

        return saida;
    },
    items: function (obj) {
        let saida = "<p>"+obj.description+"</p>";
        return saida;
    },
};

function instanciarDados(rota){
    let retorno;

    $.ajax({
        method: "GET",
        dataType: "json",
        url: baseURL + rota,
        async: false,
        success: function (data, status) {
            retorno = [...data];
        }
    });

    return retorno;
}

function carregarVisualLista(dados, pasta, chave) {
    let saida = "";

    for(let o of dados){
        let aux = "";
        if(o[chave] < 10) aux = "0";

        saida += "<div id='"+o[chave]+"'>" +
                 "   <h5> " + o.name + " </h5>" +
                 "   <img width='60px' src='"+frontURL+pasta+aux+o[chave]+".png'/>" +
                 "</div>"
    }

    detalheBox.html("");
    listaBox.html(saida);
}

function carregarVisualDetalhe(obj, dados, pasta, chave){
    let aux = obj[chave] < 10 ? "0" : "";

    let saida = "<div>" +
                "   <img src='"+frontURL+pasta+aux+obj[chave]+".png'/>" +
                "   <h2> " + obj.name + " </h2>";

    saida += Detalhe[dados](obj);

    saida +=    "</div>";
    return saida;
}

function alterarDetalhe(id, dados, pasta, chave) {
    let dadosDetalhes = Dados[dados].find((e) => e[chave] == id);
    let saida = carregarVisualDetalhe(dadosDetalhes, dados, pasta, chave);

    detalheBox.html(saida);
}

$(document).ready(function () {
    Dados.unidades = instanciarDados("unidade/all");
    Dados.sinergias = instanciarDados("sinergia/all");
    Dados.items = instanciarDados("item/all");

    let [dados, pasta, chave] = dadoSelecionado.val().split("-");
    carregarVisualLista(Dados[dados], pasta, chave);

    dadoSelecionado.change(function (e) {
        [dados, pasta, chave] = $(this).val().split("-");

        carregarVisualLista(Dados[dados], pasta, chave);
    });

    listaBox.on('click', 'div', function () {
        let dadosDetalhes = Dados[dados].find((e) => e[chave] == this.id);
        let saida = carregarVisualDetalhe(dadosDetalhes, dados, pasta, chave);

        listaBox.html("");
        detalheBox.html(saida);
    });
});