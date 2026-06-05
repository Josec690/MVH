// Script atualizado para a nova camada visual (Tailwind).
// Fornece: filtragem local dos cards, botão recarregar (restaura), e modal dinâmico de detalhes.

(function(){
    // seletor do campo de filtro (usa placeholder como fallback)
    const filterInput = document.querySelector('input[placeholder*="Filtrar"]');
    // encontrar os botões Filtrar / Recarregar próximos ao input
    function findButtonByText(text){
        return Array.from(document.querySelectorAll('button'))
            .find(b => b.textContent && b.textContent.trim().toLowerCase().includes(text.toLowerCase()));
    }
    const btnFiltrar = findButtonByText('Filtrar');
    const btnRecarregar = findButtonByText('Recarregar');

    // todos os cards com classe glass-card representam equipamentos nessa versão
    function getAllCards(){
        return Array.from(document.querySelectorAll('.glass-card'));
    }

    function aplicarFiltro(query){
        const q = (query||'').trim().toLowerCase();
        const cards = getAllCards();
        if(!q){
            cards.forEach(c=> c.style.display='');
            return;
        }
        cards.forEach(card => {
            const texto = card.innerText.toLowerCase();
            const tag = (card.querySelector('span') ? card.querySelector('span').innerText : '').toLowerCase();
            const match = texto.includes(q) || tag.includes(q);
            card.style.display = match ? '' : 'none';
        });
    }

    if(btnFiltrar){
        btnFiltrar.addEventListener('click', (e)=>{
            e.preventDefault();
            const q = filterInput ? filterInput.value : '';
            aplicarFiltro(q);
        });
    }
    if(filterInput){
        filterInput.addEventListener('keyup', (e)=>{
            if(e.key === 'Enter') aplicarFiltro(filterInput.value);
        });
    }
    if(btnRecarregar){
        btnRecarregar.addEventListener('click', (e)=>{
            e.preventDefault();
            if(filterInput) filterInput.value = '';
            aplicarFiltro('');
        });
    }

    // Cria modal dinâmico quando necessário
    function criarModal(){
        if(document.getElementById('msx-modal-overlay')) return;
        const overlay = document.createElement('div');
        overlay.id = 'msx-modal-overlay';
        overlay.addEventListener('click', (ev)=>{ if(ev.target === overlay) overlay.remove(); });

        const modal = document.createElement('div');
        modal.id = 'msx-modal';

        const close = document.createElement('button');
        close.className = 'close-btn';
        close.innerHTML = '&#x2715;';
        close.addEventListener('click', ()=> overlay.remove());

        const title = document.createElement('h3');
        title.id = 'msx-modal-title';

        const body = document.createElement('div');
        body.id = 'msx-modal-body';

        modal.appendChild(close);
        modal.appendChild(title);
        modal.appendChild(body);
        overlay.appendChild(modal);
        document.body.appendChild(overlay);
        return {overlay, modal, title, body};
    }

    // Abre modal preenchendo com dados do card (extraídos do DOM)
    function abrirModalPorCard(card){
        const img = card.querySelector('img');
        const titulo = card.querySelector('h3') ? card.querySelector('h3').innerText : 'Detalhes';
        // tenta extrair ano / fabricante / geração a partir do texto do card
        const texto = card.innerText;
        const anoMatch = texto.match(/\b(19|20)\d{2}\b/);
        const ano = anoMatch ? anoMatch[0] : 'N/A';
        const geracaoEl = Array.from(card.querySelectorAll('span')).find(s=>/MSX/i.test(s.innerText));
        const geracao = geracaoEl ? geracaoEl.innerText : ( (texto.match(/msx[0-9+\-]*/i)||[])[0] || 'N/A' );
        // fabricante tentativa
        let fabricante = 'N/A';
        const fabMatch = texto.match(/Fabricante:\s*([^\n]+)/i) || texto.match(/Fabricante:\s*([^\r]+)/i);
        if(fabMatch && fabMatch[1]) fabricante = fabMatch[1].trim();

        const created = criarModal();
        created.title.textContent = titulo;
        created.body.innerHTML = '';

        const meta = document.createElement('div'); meta.className='meta';
        meta.innerHTML = `<strong>Ano:</strong> ${ano} &nbsp; • &nbsp; <strong>Geração:</strong> ${geracao} &nbsp; • &nbsp; <strong>Fabricante:</strong> ${fabricante}`;
        created.body.appendChild(meta);

        if(img && img.src){
            const imgEl = document.createElement('img'); imgEl.src = img.src; imgEl.alt = titulo;
            created.body.appendChild(imgEl);
        }

        // tenta copiar parágrafos descritivos (se existirem)
        const paras = Array.from(card.querySelectorAll('p'));
        if(paras.length){
            const desc = document.createElement('div');
            paras.forEach(p=>{ const copy = document.createElement('p'); copy.innerText = p.innerText; desc.appendChild(copy); });
            created.body.appendChild(desc);
        }
    }

    // Anexar handlers para todos os links 'Ver Detalhes' dentro dos cards
    function bindDetalhes(){
        const anchors = Array.from(document.querySelectorAll('a'))
            .filter(a=> a.textContent && a.textContent.trim().toLowerCase().includes('ver detalhes'));
        anchors.forEach(a=>{
            a.addEventListener('click', (e)=>{
                e.preventDefault();
                e.stopPropagation();
                const card = a.closest('.glass-card');
                if(card) abrirModalPorCard(card);
            });
        });
    }

    // Inicialização
    document.addEventListener('DOMContentLoaded', ()=>{
        bindDetalhes();
        // filtro inicial limpa
        aplicarFiltro('');
    });

})();
