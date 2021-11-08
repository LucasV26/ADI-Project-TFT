const baseURL = "http://localhost:8080/";
const frontURL = "http://localhost:63342/tft-api/frontend/static/img/";
const jogadoresBox = $("#lista-jogos");
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
            saida += `<div>
                        <p>Partida ${i+1}: ${data[i]}</p> <br/> <input class="partida" type="button" id="${data[i]}" value="Acessar"/>
                      </div>`;
        }

        partidaBox.html("");
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
    partidaBox.html("<br/><br/> <h1>Carregando...</h1>");
    Builds = [];

    $.getJSON(`${baseURL}api/${id}`, function (data, status) {
        let info = data.info;
        let count = 0;
        saida = "";
        saida += `<div id="partida-status">
                      <h3>Versão do Jogo: ${info.tft_set_number}</h3>
                      <h1>Jogadores</h1>
                  </div>`;

        saida += "<div id='jogadores'>";

        info.participants.sort((a, b) => (a.placement > b.placement) ? 1 : (b.placement > a.placement) ? -1 : 0);
        for(let j of info.participants){
            saida += `<div class="jogador">
                          <div>
                              <h2>Nome: ${j.name}</h2>
                              <h3>Colocação: ${j.placement}</h3>
                          </div>
                          <div class="build">`;

            for(let u of j.units){
                let nome = u.character_id.split("_")[1];
                    saida += `<div>
                                  <div>
                                      <p>${nome}</p>
                                      <img src="${frontURL}champions/${u.character_id}.png" alt="Campeão ${nome}"/>
                                  </div>`;

                    saida += u.items.length ? `<div class="items">
                                                 <p>Usando</p>` : ``;

                for(let i of u.items){
                    let aux = i < 10 ? "0" : "";
                        saida += `<img src="${frontURL}items/${aux}${i}.png" alt="Item ${i}"/>`;
                }

                saida += u.items.length ? `</div>` : ``;

                saida += `   </div>`;
            }

            saida += `<input type='button' id='${count}' class="favorite-build" value="❤" title="Favoritar"/>`;
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
            botao_favoritar.removeClass("favorite-build");
            botao_favoritar.prop("title", "Já Favoritada");
            botao_favoritar.val("❌");
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