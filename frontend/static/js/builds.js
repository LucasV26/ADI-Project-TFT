const baseURL = "http://localhost:8080/";
const frontURL = "http://localhost:63342/tft-api/frontend/static/img/";
const buildList = $("#lista-builds");
const buildDetail = $("#build-detalhe");
let Builds;
let BuildAtual;

function instanciarBuilds(){
    let retorno;

    $.ajax({
        method: "GET",
        dataType: "json",
        url: `${baseURL}build/all`,
        async: false,
        success: function (data, status) {
            retorno = [...data];
        }
    });

    return retorno;
}

function carregarLista() {
    saida = "";

    for(let b of Builds){
        saida += `<div class="build">
                    <h2> ${b.name} </h2>
                    <p> ${b.description} </p>
                    <br>
                    <input type="button" class="detalhar" id="${b.id}" value="Detalhar"/>
                    <input type="button" class="apagar" id="${b.id}" value="Apagar"/>
                  </div>`;
    }

    $("#titulo").html("Suas Builds");
    buildDetail.html("");
    buildList.html(saida);
}

function atualizarLista() {
    Builds = instanciarBuilds();
    carregarLista();
}

function countMarca(objeto, id) {
    if(objeto[id])
        objeto[id] += 1;
    else
        objeto[id] = 1;
}

function handleEnter(e){
    if(e.keyCode === 13){
        atualizarBuild(BuildAtual, e.currentTarget.form.buildName.value, e.currentTarget.form.buildDescription.value)
    }
}

function carregarMarcas(objeto) {
    saida = `<div id="marcas">
              <h3> Marcas Alcançadas: </h3>`;
    for(let m in objeto){
        saida += `<div class="marca">
                    <img src="${frontURL}traits/${m}.png" alt="Sinergia ${m}"/>
                    <div>
                        <p> ${m} </p>
                        <p> Quantidade: ${objeto[m]} </p>
                    </div>
                  </div>`
    }
    saida +=`</div>`;

    return saida;
}

function atualizarBuild(build, novoNome, novaDescricao) {
    build.name = novoNome;
    build.description = novaDescricao;
    delete build.buildConstructs;

    $.ajax({
        contentType: 'application/json',
        data: JSON.stringify(build),
        dataType: 'json',
        success: function (data, status) {
            window.alert("Build atualizada com sucesso!");
            Builds = instanciarBuilds();
            detalharBuild(Builds.find((b) => b.id == data.id));
        },
        type: 'PATCH',
        url: `${baseURL}build/update`
    });
}

function detalharBuild(build) {
    BuildAtual = build;
    let buildConstructs = build.buildConstructs;
    let marcas = {};

    saida = "<input type='button' id='voltar' value='Voltar' onclick='atualizarLista()'/>";

    saida += `<div id="info">
                <form>
                    <div>
                        <label>Nome:</label><br/><input type="text"  value="${build.name}" id="bName"  name="buildName"/>
                    </div>
                    <div>
                        <label>Descrição:</label><br/><input type="text" value="${build.description}" id="bDescription"  name="buildDescription"/>
                    </div>
                    <input type="button" value="Atualizar" id="atualizar">
                </form>
              </div>`;

    saida += `<div id="composition">
                <h1> Composição: </h1>`;

    for(let bc of buildConstructs){
        let unidade = bc.unidade;
        let items = bc.items;
        let sinergias = unidade.traits;


        saida += `<div class="unidade">
                     <h3> ${unidade.name} </h3>
                     <img src="${frontURL}champions/${unidade.championId}.png" alt="Campeao ${unidade.name}"/>
                     <h4> Sinergias: </h4>`;

        saida += `<div class="sinergias">`;
        for(let s of sinergias){
            let sinNome = s.split('_')[1];
            countMarca(marcas, sinNome);
            saida += `<div class="sinergia">
                          <p> ${sinNome} </p>
                          <img src="${frontURL}traits/${sinNome}.png" alt="Sinergia ${sinNome}"/>
                      </div>`;
        }
        saida += `   </div>`;

        saida += `   <div class="items">`;
        if(items.length != 0)
            saida += `<h4> Items: </h4>`;
        for(let i of items){
            let aux = i < 10 ? "0" : "";
            saida += `<div class="item">
                        <img src="${frontURL}items/${aux}${i}.png" alt="Item TFT"/>
                      </div>`;
        }
        saida += `   </div>`;

        saida += `</div>`;
    }

    saida += carregarMarcas(marcas);

    saida += `</div>`;

    $("#titulo").html("");
    buildList.html("");
    buildDetail.html(saida);
}

function apagarBuild(id) {
    if(window.confirm(`Deseja mesmo apagar a build selecionada?!`))
        $.ajax({
            method: "DELETE",
            url: `${baseURL}build/${id}`,
            success: function (data, status) {
                window.alert(data);
                atualizarLista();
            }
        });
}

$(document).ready(function () {
    atualizarLista()

    buildList.on('click', 'input.detalhar', function () { detalharBuild(Builds.find((b) => b.id == this.id)); });
    buildList.on('click', 'input.apagar', function () { apagarBuild(this.id); });
    buildDetail.on('keyup', 'input#bName', handleEnter.bind(this));
    buildDetail.on('keyup', 'input#bDescription', handleEnter.bind(this));
    buildDetail.on('click', 'input#atualizar', function () { atualizarBuild(BuildAtual, this.form.buildName.value, this.form.buildDescription.value) });
});