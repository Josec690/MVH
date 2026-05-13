async function carregarEquipamentos() {
    const container = document.getElementById('equipamentos');
    container.innerHTML = 'Carregando...';

    try {
        const resposta = await fetch('/api/equipamentos');
        const dados = await resposta.json();

        if (!dados.length) {
            container.innerHTML = '<p>Nenhum equipamento encontrado.</p>';
            return;
        }

        container.innerHTML = dados.map(eq => `
      <div class="item">
        <h3>${eq.modelo}</h3>
        <p><strong>Ano:</strong> ${eq.ano}</p>
        <p><strong>Geração:</strong> ${eq.geracao}</p>
        <p><strong>Fabricante:</strong> ${eq.fabricante ? eq.fabricante.nome : 'N/A'}</p>
      </div>
    `).join('');
    } catch (erro) {
        container.innerHTML = '<p>Erro ao carregar os dados da API.</p>';
        console.error(erro);
    }
}