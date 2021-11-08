const baseURL = "http://localhost:8080/";
const frontURL = "http://localhost:63342/tft-api/frontend/static/img/";
const listaBox = $("#lista-data");
const detalheBox = $("#detalhe-data");
let Dados = {};
let Detalhe = {
    unidades: function (obj) {
        let saida = `<h3> Sinergias: </h3>
                        <div id='sinergias'>`;

        for(let s of obj.traits) {
            let nome = s.split("_")[1];
            saida +=    `<div id='${nome}' onclick='alterarDetalhe(this.id, "sinergias", "traits/", "name")'>
                            <h5> ${nome} </h5> 
                            <img src='${frontURL}/traits/${nome}.png' alt='Sinergia ${nome}'/> 
                         </div>`;
        }

        saida +=    "</div>";
        return saida;
    },
    sinergias: function (obj) {
        let saida = `<div style="padding: 10px">
                        <p>${obj.description}</p>`;
        if(obj.innate) saida += `<br/><p>${obj.innate}</p>`;
        saida += `   </div>`;

        saida +=    "<div>"
        for(let s of obj.sets){
            saida += `<p><b>Estilo: ${s.style}</b> quando possuir no mínimo ${s.min} Campeões ${obj.name}</p><br/>`;
        }
        saida +=    `</div>
                     <div id="camp-grid">
                         <h3> Campeões: </h3>
                         <div id='campeoes'>`;
        for(let c of obj.unidades){
            saida +=    `<div id='${c.championId}' onclick='alterarDetalhe(this.id, \"unidades\", \"champions/\", \"championId\")'>
                            <h5> ${c.name} </h5>
                            <img src='${frontURL}champions/${c.championId}.png'/>
                        </div>`;
        }
        saida +=    `    </div>
                     </div>`;

        return saida;
    },
    items: function (obj) {
        let saida = `<p>"${obj.description}"</p>`;
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
    detalheBox.css({"background-color": "white"});
    let saida = "";

    for(let o of dados){
        let aux = o[chave] < 10 ? "0" : "";

        saida += `<div id='${o[chave]}'>
                    <h5> ${o.name} </h5>
                    <img src='${frontURL + pasta + aux + o[chave]}.png'/>
                 </div>`;
    }

    detalheBox.html("");
    listaBox.html(saida);
}

function carregarVisualDetalhe(obj, dados, pasta, chave){
    detalheBox.css({"background-color": "#1d1e20"});
    let aux = obj[chave] < 10 ? "0" : "";

    let saida = `<div> <p id="voltar"> Voltar </p> </div>
                 <div id="detalhes-${dados}">
                   <div id="detalhes-header">
                       <img src='${frontURL + pasta + aux + obj[chave]}.png'/>
                       <h2> ${obj.name} </h2>`;
    saida += obj.cost ? `<h4> Custo: ${obj.cost} </h4>` : ``;
    saida += obj.type ? `<h4>Tipo: ${obj.type}</h4>` : ``;
    saida += `     </div>`;

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

    detalheBox.on('click', 'p#voltar', function () {
        $("#select-container").css({"display": "flex"});
        carregarVisualLista(Dados[dados], pasta, chave);
    });

    $("input[name='select']").change(function () {
        [dados, pasta, chave] = $(this).val().split("-");

        carregarVisualLista(Dados[dados], pasta, chave);
    });

    listaBox.on('click', 'div', function () {
        let dadosDetalhes = Dados[dados].find((e) => e[chave] == this.id);
        let saida = carregarVisualDetalhe(dadosDetalhes, dados, pasta, chave);

        $("#select-container").css({"display": "none"});
        listaBox.html("");
        detalheBox.html(saida);
    });
});