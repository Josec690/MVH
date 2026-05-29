const API_URL = 'http://localhost:8080/api';

// Função para mostrar/ocultar seções
function mostrarSecao(secaoId, botaoAtivo) {
    document.querySelectorAll('.secao').forEach(s => s.classList.remove('ativa'));
    document.getElementById(secaoId).classList.add('ativa');

    document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
    if (botaoAtivo) botaoAtivo.classList.add('active');


    if (secaoId === 'equipamentos') carregarEquipamentos();
    if (secaoId === 'fabricantes') carregarFabricantes();
    if (secaoId === 'emuladores') carregarEmuladores();
}

// Carregar Equipamentos
async function carregarEquipamentos() {
    const container = document.getElementById('equipamentos-container');
    container.innerHTML = '<p class="carregando">Carregando...</p>';

    try {
        const resposta = await fetch(`${API_URL}/equipamentos`);
        if (!resposta.ok) throw new Error('Erro ao carregar equipamentos');

        const dados = await resposta.json();

        if (!dados || dados.length === 0) {
            container.innerHTML = '<p class="aviso">Nenhum equipamento encontrado.</p>';
            return;
        }

        container.innerHTML = dados.map(eq => `
            <div class="card-equipamento" onclick="exibirDetalhes(${eq.id_equipamento})">
                <h3>${eq.modelo}</h3>
                <p><strong>Ano:</strong> ${eq.ano}</p>
                <p><strong>Geração:</strong> ${eq.geracao}</p>
                <p><strong>Fabricante:</strong> ${eq.fabricante ? eq.fabricante.nome : 'N/A'}</p>
                <button onclick="event.stopPropagation(); abrirModalDetalhes(${eq.id_equipamento})" class="btn-detalhes">
                    Ver Detalhes
                </button>
            </div>
        `).join('');
    } catch (erro) {
        container.innerHTML = '<p class="erro">Erro ao carregar os dados da API.</p>';
        console.error('Erro:', erro);
    }
}

// Filtrar por Geração
async function filtrarPorGeracao() {
    const geracao = document.getElementById('filtroGeracao').value.trim();
    if (!geracao) {
        carregarEquipamentos();
        return;
    }

    const container = document.getElementById('equipamentos-container');
    container.innerHTML = '<p class="carregando">Filtrando...</p>';

    try {
        const resposta = await fetch(`${API_URL}/equipamentos/geracao/${geracao}`);
        if (!resposta.ok) throw new Error('Nenhum equipamento encontrado');

        const dados = await resposta.json();

        if (!dados || dados.length === 0) {
            container.innerHTML = `<p class="aviso">Nenhum equipamento encontrado para a geração "${geracao}".</p>`;
            return;
        }

        container.innerHTML = dados.map(eq => `
            <div class="card-equipamento" onclick="abrirModalDetalhes(${eq.id_equipamento})">
                <h3>${eq.modelo}</h3>
                <p><strong>Ano:</strong> ${eq.ano}</p>
                <p><strong>Geração:</strong> ${eq.geracao}</p>
                <p><strong>Fabricante:</strong> ${eq.fabricante ? eq.fabricante.nome : 'N/A'}</p>
                <button onclick="event.stopPropagation()" class="btn-detalhes">Ver Detalhes</button>
            </div>
        `).join('');
    } catch (erro) {
        container.innerHTML = '<p class="erro">Erro ao filtrar os dados.</p>';
        console.error('Erro:', erro);
    }
}

// Carregar Fabricantes
async function carregarFabricantes() {
    const container = document.getElementById('fabricantes-container');
    container.innerHTML = '<p class="carregando">Carregando...</p>';

    try {
        const resposta = await fetch(`${API_URL}/fabricantes`);
        if (!resposta.ok) throw new Error('Erro ao carregar fabricantes');

        const dados = await resposta.json();

        if (!dados || dados.length === 0) {
            container.innerHTML = '<p class="aviso">Nenhum fabricante encontrado.</p>';
            return;
        }

        container.innerHTML = dados.map(fab => `
            <div class="card-fabricante">
                <h3>${fab.nome}</h3>
                <p><strong>País:</strong> ${fab.pais}</p>
                <p><strong>Equipamentos:</strong> ${fab.equipamentos ? fab.equipamentos.length : 0}</p>
            </div>
        `).join('');
    } catch (erro) {
        container.innerHTML = '<p class="erro">Erro ao carregar fabricantes.</p>';
        console.error('Erro:', erro);
    }
}

// Carregar Emuladores
async function carregarEmuladores() {
    const container = document.getElementById('emuladores-container');
    container.innerHTML = '<p class="carregando">Carregando...</p>';

    try {
        const resposta = await fetch(`${API_URL}/emuladores`);
        if (!resposta.ok) throw new Error('Erro ao carregar emuladores');

        const dados = await resposta.json();

        if (!dados || dados.length === 0) {
            container.innerHTML = '<p class="aviso">Nenhum emulador encontrado.</p>';
            return;
        }

        container.innerHTML = dados.map(emu => `
            <div class="card-emulador">
                <h3>${emu.nome_emu}</h3>
                <p><strong>Versão:</strong> ${emu.versao}</p>
                <p><strong>Desenvolvido por:</strong> ${emu.fabricante ? emu.fabricante.nome : 'N/A'}</p>
            </div>
        `).join('');
    } catch (erro) {
        container.innerHTML = '<p class="erro">Erro ao carregar emuladores.</p>';
        console.error('Erro:', erro);
    }
}

// Abrir Modal com Detalhes Completos
async function abrirModalDetalhes(equipamentoId) {
    const modal = document.getElementById('modal');
    const titulo = document.getElementById('modal-titulo');
    const corpo = document.getElementById('modal-corpo');

    try {
        const eq = await fetch(`${API_URL}/equipamentos/${equipamentoId}`).then(r => r.json());
        const curiosidades = await fetch(`${API_URL}/curiosidades/equipamento/${equipamentoId}`).then(r => r.json());
        const imagens = await fetch(`${API_URL}/imagens/equipamento/${equipamentoId}`).then(r => r.json());
        const fontes = await fetch(`${API_URL}/fontes/equipamento/${equipamentoId}`).then(r => r.json());

        titulo.textContent = eq.modelo;
        corpo.innerHTML = `
            <div class="modal-detalhes">
                <h4>Informações Básicas</h4>
                <p><strong>Modelo:</strong> ${eq.modelo}</p>
                <p><strong>Ano:</strong> ${eq.ano}</p>
                <p><strong>Geração:</strong> ${eq.geracao}</p>
                <p><strong>Fabricante:</strong> ${eq.fabricante ? eq.fabricante.nome : 'N/A'} (${eq.fabricante ? eq.fabricante.pais : 'N/A'})</p>

                ${imagens && imagens.length > 0 ? `
                    <h4>Imagens</h4>
                    <ul class="lista-imagens">
                        ${imagens.map(img => `
                            <li>
                                <strong>Formato:</strong> ${img.formato} | 
                                <strong>Tamanho:</strong> ${img.tamanho} | 
                                <strong>Resolução:</strong> ${img.resolucao}
                                
                                <!-- 
                                  Observação: o banco hoje guarda apenas metadados (formato/tamanho/resolucao).
                                  Para renderizar de fato, é necessário ter URL/arquivo (ex: campo 'url').
                                  Por enquanto mostramos um placeholder.
                                -->
                                <div class="imagem-placeholder" style="margin-top:8px;">
                                    <span>Exemplo (imagem genérica):</span>
                                    <img
                                        src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Computer_icon_green.svg/256px-Computer_icon_green.svg.png"
                                        alt="Imagem exemplo"
                                        style="max-width:160px; width:100%; height:auto; display:block; margin-top:6px; border-radius:6px;"
                                    />
                                </div>
                                <div class="imagem-src">
                                    <small>
                                        Se você adicionar no backend um campo URL da imagem (ex: <code>url</code>), basta retornar no endpoint e trocar aqui para: <code><img src="${img.url}"></code>.
                                    </small>
                                </div>
                            </li>
                        `).join('')}
                    </ul>
                ` : ''}


                ${curiosidades && curiosidades.length > 0 ? `
                    <h4>Curiosidades</h4>
                    <ul class="lista-curiosidades">
                        ${curiosidades.map(cur => `<li>${cur.descricao}</li>`).join('')}
                    </ul>
                ` : ''}

                ${fontes && fontes.length > 0 ? `
                    <h4>Fontes de Informação</h4>
                    <ul class="lista-fontes">
                        ${fontes.map(fonte => `
                            <li>
                                <strong>${fonte.nome_site}:</strong>
                                <a href="${fonte.url_original}" target="_blank">${fonte.url_original}</a>
                            </li>
                        `).join('')}
                    </ul>
                ` : ''}
            </div>
        `;

        modal.style.display = 'block';
    } catch (erro) {
        corpo.innerHTML = '<p class="erro">Erro ao carregar detalhes.</p>';
        console.error('Erro:', erro);
    }
}

// Fechar Modal
function fecharModal() {
    document.getElementById('modal').style.display = 'none';
}

window.onclick = function(event) {
    const modal = document.getElementById('modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

// Carregar equipamentos ao inicializar
document.addEventListener('DOMContentLoaded', carregarEquipamentos);

// Botão de recarregar (caso você queira usar sem inline onclick)
const btnRecarregar = document.querySelector('button.btn-refresh');
if (btnRecarregar) {
    btnRecarregar.addEventListener('click', (e) => {
        e.preventDefault();
        carregarEquipamentos();
    });
}

