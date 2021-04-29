package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.opiniao.OpiniaoProjecao;
import com.zupacademy.magno.mercadolivre.pergunta.PerguntaProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * REST IN PEACE T.T no fim das contas não precisou usar, mas fica aqui sua menção honrosa
     * @param id id do produto a ser buscado
     * @return Projeção de produtos com o resultado da query
     */
    @Query(value = "select " +
            "p.nome, avg(o.nota) mediaNotas, count(o.nota) qtdNotas "+
            "from opiniao o inner join produto p on (o.produto_id = p.id) "+
            "where p.id =:id group by p.id", nativeQuery = true)
    @Deprecated
    ProdutoProjecao buscaDetalhesProduto(@Param("id") Long id);

}
