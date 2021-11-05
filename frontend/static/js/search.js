const baseURL = "http://localhost:8080/";
const frontURL = "http://localhost:63342/tft-api/frontend/static/img/";
const jogadoresBox = $("#lista-jogadores");
const partidaBox = $("#detalhes-partida");
const botaoPesquisa = $("#search");
let Builds;

function ativarBotao() {
    botaoPesquisa.prop("disabled", false);
    botaoPesquisa.val("Buscar");
}

function handleEnter(e){
    if(e.keyCode === 13){
        pesquisarPartidas();
    }
}

function pesquisarPartidas(){
    let nome = $("#search-name").val();
    let quantidade = $("#search-number").val();
    $("#erro").html("");
    botaoPesquisa.prop("disabled", true);
    botaoPesquisa.val("Carregando...");

    $.getJSON(`${baseURL}api/${nome}/${quantidade}`, function (data, status) {
        saida = "";
        for(let i = 0; i < data.length; i++){
            saida += `<p>Partida ${i+1}: ${data[i]}</p> <input class="partida" type="button" id="${data[i]}" value="Acessar"/>`;
        }

        jogadoresBox.html(saida);
        ativarBotao();
    }).fail(function () {
        jogadoresBox.html("");
        $("#erro").html(`Nome não encontrado`);
        ativarBotao();
    });
}

function carregarPartida(id) {
    jogadoresBox.html("");
    partidaBox.html("Carregando...");
    Builds = [];

    $.getJSON(`${baseURL}api/${id}`, function (data, status) {
        let info = data.info;
        let count = 0;
        saida = "";
        saida += `<h3>Versão do Jogo: ${info.tft_set_number}</h3>
                  <h2>Jogadores:</h2>`;

        saida += "<div id='jogadores'>"
        for(let j of info.participants){
            saida += `<div class="jogador">
                          <p>Nome: ${j.name}</p>
                          <p>Colocação: ${j.placement}</p>
                          <div class="build">
                            <h4> Campeões Utilizados: </h4>`;

            for(let u of j.units){
                let nome = u.character_id.split("_")[1];
                    saida += `<p>${nome}</p>
                              <img src="${frontURL}champions/${u.character_id}.png" alt="Campeão ${nome}"/>
                              <div class="items">
                                <p>Com os items:</p>`;

                for(let i of u.items){
                    let aux = i < 10 ? "0" : "";
                        saida += `<img src="${frontURL}items/${aux}${i}.png" alt="Item ${i}"/>`;
                }

                saida += "</div>";
            }

            saida += `<input type='button' id='${count}' class="favorite-build" value="Favoritar"/>`;
            Builds[count] = j.units;
            count++;

            saida += "  </div>";
            saida += "</div>";
        }
        saida += "</div>";

        partidaBox.html(saida);
    })
}

function favoritarBuild(id) {
    let data = JSON.stringify(Builds[id]);
    let botao_favoritar = $(`input#${id}`);

    botao_favoritar.prop("disabled", true);

    $.ajax({
        contentType: 'application/json',
        data: data,
        dataType: 'json',
        success: function (data, status) {
            window.alert("Build favoritada com sucesso! Visite sua aba de favoritos para vê-la!");
            botao_favoritar.val("Favoritada");
        },
        type: 'POST',
        url: `${baseURL}build/favorite`
    });

}

$(document).ready(function () {

    $("#search").click(pesquisarPartidas.bind(this));
    $("#search-name").on("keyup", handleEnter.bind(this));
    jogadoresBox.on('click', 'input.partida', function (){ carregarPartida(this.id); });
    partidaBox.on('click', 'input.favorite-build', function () { favoritarBuild(this.id) });
});